package com.example.caixakioske.TelasCadastros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.caixakioske.Adaptadores.FirebaseDAO;
import com.example.caixakioske.Produtos;
import com.example.caixakioske.R;
import com.example.caixakioske.Modelos.Produto;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroProduto extends AppCompatActivity {

    EditText etNome;
    EditText etPreco;
    Button btnCadastrarProduto;
    RadioGroup rgTipo;
    RadioButton rbComidas;
    RadioButton rbBebidas;
    RadioButton rbOutros;

    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);


        etNome = findViewById(R.id.etNomeProduto);
        etPreco = findViewById(R.id.etPreco);
        btnCadastrarProduto = findViewById(R.id.btnCadastrarProduto);
        btnCadastrarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrar();
            }
        });

        reff = FirebaseDatabase.getInstance().getReference().child("produtos");

        rgTipo = findViewById(R.id.rgTipo);
        rbComidas = findViewById(R.id.rbComidas);
        rbBebidas = findViewById(R.id.rbBebidas);
        rbOutros = findViewById(R.id.rbOutros);

    }

    private void cadastrar() {

        if (!etNome.getText().toString().isEmpty() && !etPreco.getText().toString().isEmpty()) {

            final String nome = etNome.getText().toString();
            final String preco = etPreco.getText().toString();
            int tipo = getTipo(rgTipo.getCheckedRadioButtonId());
            final String tipoString;

            if (tipo == rbComidas.getId()) {
                tipoString = "comidas";
            } else if (tipo == rbBebidas.getId()) {
                tipoString = "bebidas";
            } else {
                tipoString = "outros";
            }

            if (!preco.isEmpty() && Float.valueOf(preco) > 0.99f) {

                // Pega ID do produto no DB
                String key = reff.push().getKey();

                // Cria Novo Produto
                Produto produto = new Produto(key, nome, tipoString, Float.valueOf(preco));

                // Adiciona Produto no DB
                FirebaseDAO dao = new FirebaseDAO();
                assert key != null;
                dao.create("produtos", key, produto);

                Toast.makeText(this, "Produto Inserido", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, Produtos.class);
                startActivity(intent);

            } else {
                Toast.makeText(this, "Insira um Preço Valido!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }



    }
    private int getTipo(int id) {
        if (id == rbComidas.getId()) {
            return 1;
        } else if (id == rbBebidas.getId()) {
            return 2;
        } else {
            return 0;
        }
    }

}
