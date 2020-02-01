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

public class PainelAdmin extends AppCompatActivity {

    TextView tvNomeUsuario;
    Button btnProdutos;
    Button btnPedidos;
    Button btnMesas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_admin);

        tvNomeUsuario = findViewById(R.id.tvNomeUsuario);

        Intent intent = getIntent();

        if(intent.getStringExtra("nome") != null) {
            tvNomeUsuario.setText(intent.getStringExtra("nome"));
        }

        btnProdutos = findViewById(R.id.btnProdutos);
        btnPedidos = findViewById(R.id.btnPedidos);
        btnMesas = findViewById(R.id.btnMesas);


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
        startActivity(intent);

    }

    private void pedidos() {

        Intent intent = new Intent(this, Pedidos.class);
        startActivity(intent);

    }

    private void produtos() {
        Intent intent = new Intent(this, Produtos.class);
        startActivity(intent);
    }


}
