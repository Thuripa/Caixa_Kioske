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
import android.widget.Toast;

import com.example.caixakioske.Adaptadores.ListenerGavetas;
import com.example.caixakioske.Modelos.GavetaPedido;
import com.example.caixakioske.Modelos.Pedido;
import com.example.caixakioske.Modelos.Produto;
import com.example.caixakioske.TelasCadastros.CadastroPedido;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Pedidos extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser usuario;


    RecyclerView rvPedidos;
    FloatingActionButton foabAdicionarPedido;
    FirebaseRecyclerAdapter adapter;

    String uid;
    String caminho;
    String email;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        rvPedidos = findViewById(R.id.rvPedidos);

        // Instancia FireBase Auth
        mAuth = FirebaseAuth.getInstance();
        // Pega Usuario
        usuario = mAuth.getCurrentUser();

        if(usuario == null) {
            Toast.makeText(this, "Necessário Logar", Toast.LENGTH_SHORT).show();
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        }

        // Email
        email = usuario.getEmail();
        // UID
        uid = usuario.getUid();

        intent = getIntent();

        // Verifica o Caminho
        if(intent.getStringExtra("caminho") != null) {
            caminho = getIntent().getStringExtra("caminho");
        }

        // Pega Referencia do Banco de Dados
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        Query query;
        if(caminho.equals("painelAdmin")) {
            // Cria Querry Pegando Todos os Pedidos do DB
            query = rootRef.child("pedidos");
        } else {
            query = rootRef.child("pedidos").orderByChild("uid").equalTo(usuario.getUid());
        }

        // Constroi a Configuracao do Adaptador Firebase
        FirebaseRecyclerOptions<Pedido> options =
                new FirebaseRecyclerOptions.Builder<Pedido>()
                        .setQuery(query, Pedido.class)
                        .build();

        // Cria o Objeto Adaptador Com Gaveta Customizada GavetaProduto
        adapter = new FirebaseRecyclerAdapter<Pedido, GavetaPedido>(options) {

            int i=0;

            @NonNull
            @Override
            public GavetaPedido onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.gaveta_pedido_mesa, parent, false);

                return new GavetaPedido(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull GavetaPedido holder, int position, @NonNull Pedido model) {

                holder.tvEmailPedido.setText(String.valueOf(model.getAtendente()));
                holder.tvPedidoNum.setText(String.valueOf(i++));
            }
        };



        rvPedidos.addOnItemTouchListener(new ListenerGavetas(this, rvPedidos, new ListenerGavetas.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                //On CLick Pedido

            }

            @Override
            public void onLongClick(View view, int position) {

                // On Long Click Pedido

            }
        }));


        rvPedidos.setAdapter(adapter);
        adapter.startListening();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPedidos.setLayoutManager(layoutManager);

        rvPedidos.setHasFixedSize(true);
        foabAdicionarPedido = findViewById(R.id.foabAdicionarPedido);

        foabAdicionarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarPedido();
            }
        });

    }

    private void adicionarPedido() {

        Intent cadastroPedido = new Intent(this, CadastroPedido.class);
        cadastroPedido.putExtra("caminho", "pedidos");
        startActivity(cadastroPedido);

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

    @Override
    public void onBackPressed() {

            if(caminho != null && caminho.equals("painelAdmin")) {
                Intent painelAdmin = new Intent(this, PainelAdmin.class);
                painelAdmin.putExtra("caminho", "pedidos");
                startActivity(painelAdmin);
            } else if(caminho != null && caminho.equals("painelGarcom")) {
                Intent painelGarcom = new Intent(this, PainelGarcom.class);
                painelGarcom.putExtra("caminho", "pedidos");
                startActivity(painelGarcom);
            } else {

            }
    }
}
