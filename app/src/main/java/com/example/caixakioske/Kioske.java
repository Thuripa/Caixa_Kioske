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
import android.widget.Toast;

import com.example.caixakioske.Adaptadores.FirebaseDAO;
import com.example.caixakioske.Adaptadores.ListenerGavetas;
import com.example.caixakioske.Modelos.GavetaProduto;
import com.example.caixakioske.Modelos.Produto;
import com.example.caixakioske.TelasCadastros.EditarProduto;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;

public class Kioske extends AppCompatActivity {

    RecyclerView rvKioske;
    FirebaseRecyclerAdapter adapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kioske);

        intent = getIntent();


        FirebaseDAO dao = new FirebaseDAO();
        // Cria Querry Pegando Todos os Produtos de Tipo "outros" do DB
        Query query = dao.read("produtos", "tipo", "outros");

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
                holder.itemView.setBackgroundColor(6038138);
                holder.tvNomeProduto.setText(model.getNome());
                holder.tvPrecoProduto.setText(String.valueOf(model.getPreco()));

            }
        };

        rvKioske = findViewById(R.id.rvOutros);
        rvKioske.setHasFixedSize(true);

        rvKioske.addOnItemTouchListener(new ListenerGavetas(this, rvKioske, new ListenerGavetas.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Produto produto = getItemPosition(position);

                if(produto != null) {
                    if(intent != null && intent.getStringExtra("caminho") != null) {
                        String caminho = intent.getStringExtra("caminho");
                        assert caminho != null;
                        if(caminho.equals("produtos")) {
                            //Toast.makeText(Bebidas.this, "Vindo de produtos", Toast.LENGTH_SHORT).show();
                            Intent editarProduto = new Intent(Kioske.this, EditarProduto.class);
                            editarProduto.putExtra("produto", produto);
                            editarProduto.putExtra("caminho", "outros");
                            startActivity(editarProduto);

                        } else if (caminho.equals("categorias")){
                            Toast.makeText(Kioske.this, "Vindo de categorias", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(Kioske.this,  position+ " is PRESSED successfully", Toast.LENGTH_SHORT).show();
            }
        }));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvKioske.setLayoutManager(layoutManager);

        rvKioske.setAdapter(adapter);
        adapter.startListening();

    }

    private Produto getItemPosition(int position) {
        if(adapter != null) {
            return (Produto) adapter.getItem(position);
        } else {
            return null;
        }
    }

    private String getContexto() {
        return getIntent().getStringExtra("caminho");
    }

    private void retornaActivityAnterior() {
        switch (getContexto()) {
            case "bebidas": {
                Intent intent = new Intent(this, Bebidas.class);
                intent.putExtra("caminho", "produtos");
                startActivity(intent);
                break;
            }
            case "comidas": {
                Intent intent = new Intent(this, Comidas.class);
                intent.putExtra("caminho", "produtos");
                startActivity(intent);
                break;
            }
            case "outros": {
                Intent intent = new Intent(this, Kioske.class);
                intent.putExtra("caminho", "produtos");
                startActivity(intent);
                break;
            }
            case "produtos": {
                Intent intent = new Intent(this, Produtos.class);
                intent.putExtra("caminho", "produtos");
                startActivity(intent);
                break;
            }
        }
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
        Intent intentAnterior = getIntent();

        if(intentAnterior != null) {
            String caminho = intentAnterior.getStringExtra("caminho");

            if(caminho != null) {
                if(caminho.equals("produtos")) {
                    Intent back = new Intent(this, Produtos.class);
                    startActivity(back);
                } else if (caminho.equals("categorias")) {
                    Intent back = new Intent(this, Categorias.class);
                    startActivity(back);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_voltar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_voltar) {
            retornaActivityAnterior();
        }
        return super.onOptionsItemSelected(item);
    }

}
