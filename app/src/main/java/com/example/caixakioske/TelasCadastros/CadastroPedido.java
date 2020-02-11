package com.example.caixakioske.TelasCadastros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caixakioske.Adaptadores.FirebaseDAO;
import com.example.caixakioske.Categorias;
import com.example.caixakioske.Login;
import com.example.caixakioske.Modelos.GavetaOrdem;
import com.example.caixakioske.Modelos.GavetaProduto;
import com.example.caixakioske.Modelos.Ordem;
import com.example.caixakioske.Modelos.Pedido;
import com.example.caixakioske.Modelos.Produto;
import com.example.caixakioske.PainelAdmin;
import com.example.caixakioske.PainelGarcom;
import com.example.caixakioske.Pedidos;
import com.example.caixakioske.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.Date;

public class CadastroPedido extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser usuario;
    private DatabaseReference reff;
    FirebaseRecyclerAdapter adapter;

    TextView tvEmailOndeVaiEmail;
    RecyclerView rvPedidosDoPedido;
    FloatingActionButton foabAdicionarPedido;

    Pedido pedido;

    String uid;
    String caminho;
    String email;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pedido);

        tvEmailOndeVaiEmail = findViewById(R.id.tvEmailOndeVaiEmail);
        rvPedidosDoPedido = findViewById(R.id.rvPedidosDoPedido);
        foabAdicionarPedido = findViewById(R.id.foabAdicionarPedido);

        // Instancia FireBase Auth
        mAuth = FirebaseAuth.getInstance();

        // Pega Usuario
        usuario = mAuth.getCurrentUser();
        if(usuario == null) {
            Toast.makeText(this, "Necessário Logar", Toast.LENGTH_SHORT).show();
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        }

        // Pega Email e Uid
        email = usuario.getEmail();
        uid = usuario.getUid();

        tvEmailOndeVaiEmail.setText(usuario.getEmail());

        intent = getIntent();

        // Verifica o Caminho
        if(intent != null && intent.getStringExtra("caminho") != null) {
            caminho = getIntent().getStringExtra("caminho");

            Query query;
            if(caminho.equals("produtosPedido")) {

                String key = intent.getStringExtra("key");
                // Cria Querry Pegando Todos as Ordems do pedido
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("pedidos");
                query = rootRef.equalTo(key);
                query = query.orderByChild("ordems");
                Toast.makeText(this, "Mostra Pedido", Toast.LENGTH_SHORT).show();
                setAdapter(query);

            } else {



            }
        } else {

        }

        foabAdicionarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarPedido();
            }
        });

    }

    public void setAdapter(Query query) {
        // Constroi a Configuracao do Adaptador
        FirebaseRecyclerOptions<Ordem> options =
                new FirebaseRecyclerOptions.Builder<Ordem>()
                        .setQuery(query, Ordem.class)
                        .build();

        // Cria o Objeto Adaptador Com Gaveta Customizada GavetaOrdem
        adapter = new FirebaseRecyclerAdapter<Ordem, GavetaOrdem>(options) {
            @NonNull
            @Override
            public GavetaOrdem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.gaveta_ordem, parent, false);

                return new GavetaOrdem(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull GavetaOrdem holder, int position, @NonNull Ordem model) {

                Log.d("ALCM", "Ordem"+model.getProduto().getNome());
                //holder.itemView.setBackgroundColor(255000000);
                holder.tvNomeProduto.setText(model.getProduto().getNome());
                holder.tvQuantidade.setText(model.getQuantidade());
                holder.tvPrecoTotal.setText(String.valueOf(model.getPrecoTotal()));

            }
        };

        Log.d("ALCM", "Adaptador: "+adapter);

        rvPedidosDoPedido.setAdapter(adapter);

        if(adapter != null) {
            adapter.startListening();
        } else {
            Log.d("ALCM", "Adapter NULL "+adapter);
            Toast.makeText(this, "adapter: "+adapter, Toast.LENGTH_SHORT).show();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvPedidosDoPedido.setLayoutManager(layoutManager);

        rvPedidosDoPedido.setHasFixedSize(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(adapter != null)
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter != null)
        adapter.stopListening();
    }

    private void adicionarPedido() {

        Intent intent = new Intent(this, Categorias.class);
        intent.putExtra("caminho", "cadastroPedido");
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro_pedido, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_pedir_pedido) {
            lancaPedido();
        } else if(item.getItemId() == R.id.menu_Cancelar_pedido) {



            Intent intent = new Intent(this, Pedidos.class);
            intent.putExtra("camingo", "cadastroPedido");
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void lancaPedido() {
    }

    @Override
    public void onBackPressed() {

        if(caminho != null && caminho.equals("painelAdmin")) {
            Intent painelAdmin = new Intent(this, PainelAdmin.class);
            painelAdmin.putExtra("caminho", "cadastroPedido");
            startActivity(painelAdmin);
        } else if(caminho != null && caminho.equals("painelGarcom")) {
            Intent painelGarcom = new Intent(this, PainelGarcom.class);
            painelGarcom.putExtra("caminho", "cadastroPedido");
            startActivity(painelGarcom);
        }
    }
}
