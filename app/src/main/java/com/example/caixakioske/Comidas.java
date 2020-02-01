package com.example.caixakioske;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.caixakioske.Modelos.GavetaProduto;
import com.example.caixakioske.Modelos.Produto;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Comidas extends AppCompatActivity {

    RecyclerView rvComidas;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comidas);


        // Pega Referencia do Banco de Dados
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        // Cria Querry Pegando Todos os Produtos de Tipo "Comidas" do DB
        Query query = rootRef.child("produtos").orderByChild("tipo").equalTo("comidas");

        // Constroi a Configuracao do AdaptadorProdutosRealm
        FirebaseRecyclerOptions<Produto> options =
                new FirebaseRecyclerOptions.Builder<Produto>()
                        .setQuery(query, Produto.class)
                        .build();

        // Cria o Objeto Adaptador Com Gaveta Customizada GavetaProduto
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

        rvComidas = findViewById(R.id.rvComidas);
        rvComidas.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvComidas.setLayoutManager(layoutManager);

        rvComidas.setAdapter(adapter);
        adapter.startListening();

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

}
