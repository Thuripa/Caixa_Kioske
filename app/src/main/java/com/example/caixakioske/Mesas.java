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

import com.example.caixakioske.Modelos.GavetaMesa;
import com.example.caixakioske.Modelos.GavetaProduto;
import com.example.caixakioske.Modelos.Mesa;
import com.example.caixakioske.Modelos.Produto;
import com.example.caixakioske.Telas_Cadastros.CadastroMesa;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Mesas extends AppCompatActivity {


    RecyclerView rvMesas;
    FloatingActionButton foabAdicionarMesa;
    FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesas);

        // Pega Referencia do Banco de Dados

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        // Cria Querry Pegando Todos as Mesas do DB
        Query query = rootRef
                .child("mesas");

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

    public void adicionarMesa() {
        Intent intent = new Intent(this, CadastroMesa.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, PainelAdmin.class);
        startActivity(intent);
    }
}
