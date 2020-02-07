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

import com.example.caixakioske.Adaptadores.ListenerGavetaProduto;
import com.example.caixakioske.Modelos.GavetaProduto;
import com.example.caixakioske.Modelos.Produto;
import com.example.caixakioske.TelasCadastros.EditarProduto;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Pedidos extends AppCompatActivity {

    RecyclerView rvPedidos;
    FloatingActionButton foabAdicionarPedido;
    FirebaseRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        rvPedidos = findViewById(R.id.rvPedidos);
        foabAdicionarPedido = findViewById(R.id.foabAdicionarPedido);

        foabAdicionarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarPedido();
            }
        });


        // Pega Referencia do Banco de Dados

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        // Cria Querry Pegando Todos os Produtos do DB
        Query query = rootRef.child("mesas");

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
                view.setBackgroundColor(0);

                return new GavetaProduto(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull GavetaProduto holder, int position, @NonNull Produto model) {

                holder.tvNomeProduto.setText(model.getNome());
                holder.tvPrecoProduto.setText(String.valueOf(model.getPreco()));

            }
        };

        rvPedidos.setHasFixedSize(true);

        rvPedidos.addOnItemTouchListener(new ListenerGavetaProduto(this, rvPedidos, new ListenerGavetaProduto.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Produto produto = getItemPosition(position);

                //Toast.makeText(Bebidas.this, "Vindo de produtos", Toast.LENGTH_SHORT).show();
                Intent editarProduto = new Intent(Pedidos.this, EditarProduto.class);
                editarProduto.putExtra("produto", produto);
                editarProduto.putExtra("caminho", "produtos");
                startActivity(editarProduto);

            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(Kioske.this,  position+ " is PRESSED successfully", Toast.LENGTH_SHORT).show();
            }
        }));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPedidos.setLayoutManager(layoutManager);

        rvPedidos.setAdapter(adapter);
        adapter.startListening();


    }

    private void adicionarPedido() {

        

    }

    private Produto getItemPosition(int position) {

        if(adapter != null) {
            return (Produto) adapter.getItem(position);
        } else {
            return null;
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

}
