package com.example.caixakioske.TelasCadastros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caixakioske.Adaptadores.FirebaseDAO;
import com.example.caixakioske.Categorias;
import com.example.caixakioske.Kioske;
import com.example.caixakioske.Login;
import com.example.caixakioske.Mesas;
import com.example.caixakioske.Modelos.GavetaMesa;
import com.example.caixakioske.Modelos.GavetaOrdem;
import com.example.caixakioske.Modelos.GavetaProduto;
import com.example.caixakioske.Modelos.Mesa;
import com.example.caixakioske.Modelos.Ordem;
import com.example.caixakioske.Modelos.Pedido;
import com.example.caixakioske.Modelos.Produto;
import com.example.caixakioske.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class CadastroMesa extends AppCompatActivity {

    private FirebaseAuth mAuth;

    FirebaseUser usuario;
    String email;
    String caminho;
    EditText etNomeMesa;
    Button btnPedido;
    RecyclerView rvPedidoMesa;
    FirebaseRecyclerAdapter adapter;
    String uid;

    Pedido pedido;
    Float valorMesa;

    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_mesa);

        this.etNomeMesa = findViewById(R.id.etNomeMesa);
        this.btnPedido = findViewById(R.id.btnPedidoMesa);
        this.rvPedidoMesa = findViewById(R.id.rvPedidoMesa);

        // Instancia FireBase Auth
        mAuth = FirebaseAuth.getInstance();

        // Pega Intent
        Intent intent = getIntent();

        // Verifica o Caminho
        if(intent.getStringExtra("caminho") != null) {
            caminho = getIntent().getStringExtra("caminho");
        }

        // Pega Usuario
        usuario = mAuth.getCurrentUser();

        if(usuario == null) {
            Toast.makeText(this, "Necessário Logar", Toast.LENGTH_SHORT).show();
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        }

        uid = usuario.getUid();

        // Pega Referencia DB
        reff = FirebaseDatabase.getInstance().getReference().child("mesas");

        Query query = reff;

        // Constroi a Configuracao do Adaptador
        FirebaseRecyclerOptions<Ordem> options =
                new FirebaseRecyclerOptions.Builder<Ordem>()
                        .setQuery(query, Ordem.class)
                        .build();

        // Cria o Objeto Adaptador Com Gaveta Customizada GavetaProduto
        adapter = new FirebaseRecyclerAdapter<Ordem, GavetaOrdem>(options) {
            @NonNull
            @Override
            public GavetaOrdem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.gaveta_ordem, parent, false);
                view.setBackgroundColor(0);

                return new GavetaOrdem(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull GavetaOrdem holder, int position, @NonNull Ordem model) {

                holder.tvPrecoTotal.setText(String.valueOf(model.getPrecoTotal()));
                holder.tvQuantidade.setText(model.getQuantidade());
                holder.tvNomeProduto.setText(model.getProduto().getNome());
            }
        };

        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedido();
            }
        });


    }

    private void cadastrarMesa() {

        String nomeMesa = etNomeMesa.getText().toString();

        if(uid != null && !uid.isEmpty()) {
            if(!nomeMesa.isEmpty()) {

                String key = reff.push().getKey();

                Mesa mesa = new Mesa(key, uid,nomeMesa, 0.00f);

                // Adiciona Produto no DB
                FirebaseDAO dao = new FirebaseDAO();
                assert key != null;
                dao.create("mesas", key, mesa);

                Toast.makeText(this, "Mesa Cadastrada!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, Mesas.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Insira um Nome pra Mesa", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Necessário Autenticar-se", Toast.LENGTH_SHORT).show();
            Intent login = new Intent(this, Login.class);
            startActivity(login);
        }
    }

    private void pedido() {

        Intent intent = new Intent(this, Categorias.class);
        intent.putExtra("caminho", "cadastroMesa");
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro_mesa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item ) {
        if (item.getItemId() == R.id.menu_pedir) {
            cadastrarMesa();
        } else if (item.getItemId() == R.id.menu_Cancelar) {
            // CANCELA PEDIDO
        }
        return super.onOptionsItemSelected(item);
    }
}
