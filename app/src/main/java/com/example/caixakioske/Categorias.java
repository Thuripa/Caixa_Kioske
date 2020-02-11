package com.example.caixakioske;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.caixakioske.TelasCadastros.CadastroMesa;
import com.example.caixakioske.TelasPedidos.BebidasPedido;

public class Categorias extends AppCompatActivity {

    Button btnComidas;
    Button btnBebidas;
    Button btnOutros;

    String caminho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);

        // Pega Intent
        Intent intent = getIntent();

        // Verifica o Caminho
        if(intent.getStringExtra("caminho") != null) {
            caminho = getIntent().getStringExtra("caminho");
            renomearDPS();
        }

    }

    // Método para pegar o "caminho" e decidir pra onde vai e o que fazer
    private void renomearDPS() {
        if ("cadastroMesa".equals(caminho) || "cadastroPedido".equals(caminho)) {
            btnComidas = findViewById(R.id.btnComidas);
            btnComidas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        /*Intent intent = new Intent(Categorias.this, ComidasPedido.class);
                        intent.putExtra("caminho", "categorias");
                        startActivity(intent);*/
                }
            });


            btnBebidas = findViewById(R.id.btnBebidas);
            btnBebidas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Categorias.this, BebidasPedido.class);
                    intent.putExtra("caminho", caminho);
                    startActivity(intent);
                }
            });
            btnOutros = findViewById(R.id.btnOutros);
            btnOutros.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        /*Intent intent = new Intent(Categorias.this, KioskePedido.class);
                        intent.putExtra("caminho", "categorias");
                        startActivity(intent);*/
                }
            });
        } else {

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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(caminho.equals("painelAdmin")) {
            Intent intent = new Intent(this, PainelAdmin.class);
            intent.putExtra("caminho", "categorias");
            startActivity(intent);
        } else if(caminho.equals("painelGarcom")) {
            Intent intent = new Intent(this, PainelGarcom.class);
            intent.putExtra("caminho", "categorias");
            startActivity(intent);
        } else if(caminho.equals("cadastroMesa")) {
            Intent intent = new Intent(this, CadastroMesa.class);
            intent.putExtra("caminho", "categorias");
            startActivity(intent);
        }


    }
}
