package com.example.caixakioske;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caixakioske.Modelos.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PainelAdmin extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button btnProdutos;
    Button btnPedidos;
    Button btnMesas;
    Usuario usuario;
    String uid;
    String caminho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_admin);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        usuario = intent.getParcelableExtra("usuario");

        FirebaseUser user = mAuth.getCurrentUser();

        if (usuario != null) {
            uid = usuario.getUid();
            Toast.makeText(this, "Nome:"+user.getDisplayName(), Toast.LENGTH_SHORT).show();
        }


        if(intent.getStringExtra("caminho") != null) {
            caminho = getIntent().getStringExtra("caminho");
            uid = getIntent().getStringExtra("uid");
        }

        btnProdutos = findViewById(R.id.btnProdutos);
        btnPedidos = findViewById(R.id.btnPedidos);
        btnMesas = findViewById(R.id.btnMesasAdmin);


        btnProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                produtos();
            }
        });
        btnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedidos();
            }
        });
        btnMesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mesas();
            }
        });

    }

    private void mesas() {

        Intent intent = new Intent(this, Mesas.class);
        intent.putExtra("uid", uid);
        intent.putExtra("caminho", "painelAdmin");
        startActivity(intent);

    }

    private void pedidos() {

        Intent intent = new Intent(this, Pedidos.class);
        intent.putExtra("caminho", "painelAdmin");
        intent.putExtra("uid", uid);
        startActivity(intent);

    }

    private void produtos() {
        Intent intent = new Intent(this, Produtos.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_painel_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_admin_dados) {
            Toast.makeText(this, "Dados", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.menu_admin_trocar_senha) {
            Toast.makeText(this, "Trocar Senha", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId() == R.id.menu_admin_sair_da_conta) {
            mAuth.signOut();
            Intent logout = new Intent(this, Login.class);
            startActivity(logout);
        }
        return super.onOptionsItemSelected(item);
    }

}
