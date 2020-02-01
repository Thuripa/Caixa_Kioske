package com.example.caixakioske;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Categorias extends AppCompatActivity {

    Button btnComidas;
    Button btnBebidas;
    Button btnOutros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        btnComidas = findViewById(R.id.btnComidas);
        btnBebidas = findViewById(R.id.btnBebidas);
        btnOutros = findViewById(R.id.btnOutros);


        btnComidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categorias.this, Comidas.class);
                intent.putExtra("caminho", "categorias");
                startActivity(intent);
            }
        });

        btnBebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categorias.this, Bebidas.class);
                intent.putExtra("caminho", "categorias");
                startActivity(intent);
            }
        });

        btnOutros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Categorias.this, Kioske.class);
                intent.putExtra("caminho", "categorias");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, PainelAdmin.class);
        startActivity(intent);
    }
}
