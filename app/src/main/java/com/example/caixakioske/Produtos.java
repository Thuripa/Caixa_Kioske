package com.example.caixakioske;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.caixakioske.Telas_Cadastros.CadastroProduto;
import com.example.caixakioske.Modelos.GavetaProduto;
import com.example.caixakioske.Modelos.Produto;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Produtos extends AppCompatActivity {

    RecyclerView rvProdutos;
    FloatingActionButton foabNovoProduto;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_produtos);


        // Pega Referencia do Banco de Dados

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        // Cria Querry Pegando Todos os Produtos do DB
        Query query = rootRef.child("produtos");


        // Constroi a Configuracao do AdaptadorProdutosRealm
        FirebaseRecyclerOptions<Produto> options =
                new FirebaseRecyclerOptions.Builder<Produto>()
                        .setQuery(query, Produto.class)
                        .build();

        // Cria o Objeto Adaptador Com Gaveta Customizada GavetaProduto
        adapter = new FirebaseRecyclerAdapter<Produto, GavetaProduto>(options) {
            @NonNull
            @Override
            public GavetaProduto onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.gaveta_produtos, parent, false);

                return new GavetaProduto(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull GavetaProduto holder, int position, @NonNull Produto model) {

                holder.tvNomeProduto.setText(model.getNome());
                holder.tvPrecoProduto.setText(String.valueOf(model.getPreco()));

            }
        };

        rvProdutos = findViewById(R.id.rvProdutos);
        rvProdutos.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvProdutos.setLayoutManager(layoutManager);

        rvProdutos.setAdapter(adapter);
        adapter.startListening();

        foabNovoProduto = findViewById(R.id.foabNovoProduto);
        foabNovoProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                novoProduto();
            }
        });

    }

    private void novoProduto() {
        Intent intent = new Intent (this, CadastroProduto.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PainelAdmin.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_produtos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_filtro_bebidas) {
            Intent bebidas = new Intent(this, Bebidas.class);
            bebidas.putExtra("caminho", "produtos");
            startActivity(bebidas);
        } else if (item.getItemId() == R.id.menu_filtro_comidas) {
            Intent comidas = new Intent(this, Comidas.class);
            comidas.putExtra("caminho", "produtos");
            startActivity(comidas);
        } else if(item.getItemId() == R.id.menu_filtro_outros) {
            Intent outros = new Intent(this, Kioske.class);
            outros.putExtra("caminho", "produtos");
            startActivity(outros);
        }
        return super.onOptionsItemSelected(item);
    }
}
