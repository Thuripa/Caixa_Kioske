package com.example.caixakioske;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.caixakioske.Modelos.Produto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Caixa extends AppCompatActivity {

    ArrayList<Produto> produtos = new ArrayList<>();
    RecyclerView rvPedido;
    FloatingActionButton foabAdicionarPedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caixa);

        this.rvPedido = findViewById(R.id.rvPedido);
        this.foabAdicionarPedido = findViewById(R.id.foabAdicionarPedido);

        foabAdicionarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adicionarPedido();
            }
        });


    }

    public void adicionarPedido() {

        Intent intent = new Intent(this, Categorias.class);

    }

}
