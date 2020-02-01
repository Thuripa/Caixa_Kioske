package com.example.caixakioske.Telas_Cadastros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caixakioske.Categorias;
import com.example.caixakioske.Mesas;
import com.example.caixakioske.Modelos.Mesa;
import com.example.caixakioske.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroMesa extends AppCompatActivity {

    EditText etNomeMesa;
    Button btnPedido;
    RecyclerView rvPedidoMesa;
    FloatingActionButton foabCadastrarMesa;

    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_mesa);

        this.etNomeMesa = findViewById(R.id.etNomeMesa);
        this.btnPedido = findViewById(R.id.btnPedidoMesa);
        this.rvPedidoMesa = findViewById(R.id.rvPedidoMesa);
        this.foabCadastrarMesa = findViewById(R.id.foabCadastrarMesa);


        reff = FirebaseDatabase.getInstance().getReference().child("mesas");


        foabCadastrarMesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastrarMesa();
            }
        });

        btnPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pedido();
            }
        });


    }

    private void cadastrarMesa() {

        String nomeMesa = etNomeMesa.getText().toString();

        if(!nomeMesa.equals("")) {

            Mesa mesa = new Mesa(nomeMesa, 0.00f);
            reff.push().setValue(mesa, null);

            Toast.makeText(this, "Mesa Cadastrada!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, Mesas.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Insira um Nome", Toast.LENGTH_SHORT).show();
        }




    }

    private void pedido() {

        Intent intent = new Intent(this, Categorias.class);
        startActivity(intent);

    }
}
