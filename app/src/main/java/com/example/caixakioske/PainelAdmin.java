package com.example.caixakioske;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caixakioske.Modelos.Produto;
import com.example.caixakioske.Modelos.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PainelAdmin extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button btnProdutos;
    Button btnPedidos;
    Button btnMesas;
    FirebaseUser usuario;
    String caminho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_admin);

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
        intent.putExtra("usuario", usuario);
        intent.putExtra("caminho", "painelAdmin");
        startActivity(intent);

    }

    private void pedidos() {

        Intent intent = new Intent(this, Pedidos.class);
        intent.putExtra("email", usuario.getEmail());
        intent.putExtra("caminho", "painelAdmin");
        startActivity(intent);

    }

    private void produtos() {
        Intent intent = new Intent(this, Produtos.class);
        intent.putExtra("usuario", usuario);
        intent.putExtra("caminho", "painelAdmin");
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
