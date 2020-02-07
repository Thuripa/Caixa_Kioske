package com.example.caixakioske.TelasCadastros;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caixakioske.Adaptadores.FirebaseDAO;
import com.example.caixakioske.Categorias;
import com.example.caixakioske.Kioske;
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
    String uid;

    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_mesa);

        this.etNomeMesa = findViewById(R.id.etNomeMesa);
        this.btnPedido = findViewById(R.id.btnPedidoMesa);
        this.rvPedidoMesa = findViewById(R.id.rvPedidoMesa);


        reff = FirebaseDatabase.getInstance().getReference().child("mesas");

        uid = getIntent().getStringExtra("uid");


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
            Toast.makeText(this, "Insira um Nome", Toast.LENGTH_SHORT).show();
        }




    }

    private void pedido() {

        Intent intent = new Intent(this, Categorias.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cadastro_mesa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_pedir) {
            cadastrarMesa();
        } else if (item.getItemId() == R.id.menu_Cancelar) {
            // CANCELA PEDIDO
        }
        return super.onOptionsItemSelected(item);
    }
}
