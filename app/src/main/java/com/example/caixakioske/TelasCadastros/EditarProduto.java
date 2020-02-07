package com.example.caixakioske.TelasCadastros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.caixakioske.Adaptadores.FirebaseDAO;
import com.example.caixakioske.Bebidas;
import com.example.caixakioske.Comidas;
import com.example.caixakioske.Kioske;
import com.example.caixakioske.Modelos.Produto;
import com.example.caixakioske.Produtos;
import com.example.caixakioske.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EditarProduto extends AppCompatActivity {

    Produto produto;
    DatabaseReference reff;
    EditText etEditNomeProduto;
    EditText etEditPrecoProduto;
    Button btnEditarProduto;
    RadioGroup rgEditTipo;
    RadioButton rbEditComidas;
    RadioButton rbEditBebidas;
    RadioButton rbEditOutros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_produto);



        etEditNomeProduto = findViewById(R.id.etEditNomeProduto);
        etEditPrecoProduto = findViewById(R.id.etEditPrecoProduto);
        rgEditTipo = findViewById(R.id.rgEditTipo);
        rbEditComidas = findViewById(R.id.rbEditComidas);
        rbEditBebidas = findViewById(R.id.rbEditBebidas);
        rbEditOutros = findViewById(R.id.rbEditOutros);
        btnEditarProduto = findViewById(R.id.btnEditarProduto);
        btnEditarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chamaEditar();
            }
        });

        getProduto();

    }

    private void getProduto() {
        // "pega" o produto
        produto = getIntent().getParcelableExtra("produto");

        // Preenche campos com infos do produto
        if(produto != null) {
            etEditNomeProduto.setText(produto.getNome());
            etEditPrecoProduto.setText(String.valueOf(produto.getPreco()));
            String tipo = produto.getTipo();
            Toast.makeText(this, ""+tipo, Toast.LENGTH_SHORT).show();
            if(tipo.equals("comidas")) {
                rbEditComidas.toggle();
            } else if(tipo.equals("bebidas")) {
                rbEditBebidas.toggle();
            } else if (tipo.equals("outros")){
                rbEditOutros.toggle();
            }

        }
    }

    private void excluir() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.MensagemExcluir)
                .setTitle(R.string.Excluir);

        builder.setPositiveButton(R.string.Excluir, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // "pega" referencia do Produto no DB
                reff = FirebaseDatabase.getInstance().getReference().child("produtos").child(produto.getId());
                // Remove Produto
                reff.removeValue();

                Toast.makeText(EditarProduto.this, "Produto Excluído", Toast.LENGTH_SHORT).show();
                retornaActivityAnterior();

            }
        });
        builder.setNegativeButton(R.string.Cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Usuario Cancelou Exclusão
                dialog.cancel();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void chamaEditar() {
        if(etEditNomeProduto!=null && etEditPrecoProduto != null && rgEditTipo != null) {

            String nome;

            if (!TextUtils.isEmpty(etEditNomeProduto.getText().toString().trim())) {
                nome = etEditNomeProduto.getText().toString().trim();
            } else {
                nome = "Sem Nome";
            }

            String categoria = getCategoria();
            Float preco = Float.valueOf(etEditPrecoProduto.getText().toString());
            String id = produto.getId();


            editar(id ,nome, categoria, preco);
        }
    }

    private void editar(String id, String nome, String categoria, Float preco) {

        // Pega a referencia do produto escolhido
        FirebaseDAO dao = new FirebaseDAO();
        reff = dao.getReference("produtos", produto.getId());

        Produto produto = new Produto(id, nome, categoria, preco);

        reff.setValue(produto);

        Toast.makeText(this, "Produto Atualizado", Toast.LENGTH_SHORT).show();

        retornaActivityAnterior();

    }

    private void retornaActivityAnterior() {
        switch (getContexto()) {
            case "bebidas": {
                Intent intent = new Intent(this, Bebidas.class);
                intent.putExtra("caminho", "produtos");
                startActivity(intent);
                break;
            }
            case "comidas": {
                Intent intent = new Intent(this, Comidas.class);
                intent.putExtra("caminho", "produtos");
                startActivity(intent);
                break;
            }
            case "outros": {
                Intent intent = new Intent(this, Kioske.class);
                intent.putExtra("caminho", "produtos");
                startActivity(intent);
                break;
            }
            case "produtos": {
                Intent intent = new Intent(this, Produtos.class);
                intent.putExtra("caminho", "produtos");
                startActivity(intent);
                break;
            }
        }
    }

    private String getCategoria() {

        if(rgEditTipo.getCheckedRadioButtonId() == rbEditComidas.getId()) {
            return "comidas";
        }

        if(rgEditTipo.getCheckedRadioButtonId() == rbEditBebidas.getId()) {
            return "bebidas";
        }

        if(rgEditTipo.getCheckedRadioButtonId() == rbEditOutros.getId()) {
            return "outros";
        }

        return "outros";
    }

    private String getContexto() {
        return getIntent().getStringExtra("caminho");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editar_produto, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_opcoes_salvar) {
            chamaEditar();
        } else if (item.getItemId() == R.id.menu_opcoes_excluir) {
            excluir();
        } else if(item.getItemId() == R.id.menu_opcoes_voltar) {
            retornaActivityAnterior();
        }
        return super.onOptionsItemSelected(item);
    }
}
