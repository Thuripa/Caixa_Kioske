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
import com.example.caixakioske.Modelos.GavetaMesa;
import com.example.caixakioske.Modelos.Mesa;
import com.example.caixakioske.Modelos.Produto;
import com.example.caixakioske.TelasCadastros.CadastroMesa;
import com.example.caixakioske.TelasCadastros.EditarProduto;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Mesas extends AppCompatActivity {

    String uid;
    String caminho;
    RecyclerView rvMesas;
    FloatingActionButton foabAdicionarMesa;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);

        // Pega Referencia do Banco de Dados

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        uid = getIntent().getStringExtra("uid");
        caminho = getIntent().getStringExtra("caminho");

        // Cria Querry Pegando Todos as Mesas do DB Filtrado pela UID
        Query query = rootRef
                .child("mesas")
                .orderByChild("idAtendente")
                .equalTo("uid");

        // Constroi a Configuracao do Adaptador
        FirebaseRecyclerOptions<Mesa> options =
                new FirebaseRecyclerOptions.Builder<Mesa>()
                        .setQuery(query, Mesa.class)
                        .build();

        // Cria o Objeto Adaptador Com Gaveta Customizada Mesa
        adapter = new FirebaseRecyclerAdapter<Mesa, GavetaMesa>(options) {
            @Override
            protected void onBindViewHolder(@NonNull GavetaMesa holder, int position, @NonNull Mesa model) {
                holder.tvNomeMesa.setText(String.valueOf(model.getNome()));
                holder.tvValorMesa.setText(String.valueOf(model.getValorMesa()));
            }

            @NonNull
            @Override
            public GavetaMesa onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.gaveta_mesa, parent, false);

                return new GavetaMesa(view);
            }
        };

        rvMesas = findViewById(R.id.rvMesas);
        rvMesas.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvMesas.setLayoutManager(layoutManager);

        rvMesas.addOnItemTouchListener(new ListenerGavetaProduto(this, rvMesas, new ListenerGavetaProduto.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Mesa mesa = getItemPosition(position);

                //Toast.makeText(Bebidas.this, "Vindo de produtos", Toast.LENGTH_SHORT).show();
                Intent editarProduto = new Intent(Mesas.this, CadastroMesa.class);
                editarProduto.putExtra("mesa", mesa);
                editarProduto.putExtra("caminho", "mesas");
                startActivity(editarProduto);

            }

            @Override
            public void onLongClick(View view, int position) {
                //Toast.makeText(Kioske.this,  position+ " is PRESSED successfully", Toast.LENGTH_SHORT).show();
            }
        }));

        rvMesas.setAdapter(adapter);
        adapter.startListening();

        foabAdicionarMesa = findViewById(R.id.foabAdicionarMesa);
        foabAdicionarMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarMesa();
            }
        });
    }

    private Mesa getItemPosition(int position) {

        if(adapter != null) {
            return (Mesa) adapter.getItem(position);
        } else {
            return null;
        }

    }

    public void adicionarMesa() {
        Intent intent = new Intent(this, CadastroMesa.class);
        intent.putExtra("uid", uid);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(caminho.equals("painelAdmin")) {
            Intent intent = new Intent(this, PainelAdmin.class);
            startActivity(intent);
        }
        if(caminho.equals("painelGarcom")) {
            Intent intent = new Intent(this, PainelGarcom.class);
            startActivity(intent);
        }


    }
}
