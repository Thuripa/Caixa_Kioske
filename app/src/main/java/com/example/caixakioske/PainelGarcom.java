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

public class PainelGarcom extends AppCompatActivity {

    private FirebaseAuth mAuth;

    Button btnMesas;
    Button btnHistorico;
    String uid;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painel_garcom);

        mAuth = FirebaseAuth.getInstance();


        Intent intent = getIntent();
        if (intent != null) {
            usuario = intent.getParcelableExtra("usuario");
            uid = intent.getStringExtra("uid");
        }

        this.btnMesas = findViewById(R.id.btnMesasGarcom);
        this.btnHistorico = findViewById(R.id.btnHistoricoGarcom);

        btnMesas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PainelGarcom.this, Mesas.class);
                intent.putExtra("uid", uid);
                intent.putExtra("caminho", "painelGarcom");
                startActivity(intent);
            }
        });

        btnHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_painel_garcom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_garcom_dados) {
            Toast.makeText(this, "Dados", Toast.LENGTH_SHORT).show();
        } else if (item.getItemId() == R.id.menu_garcom_trocar_senha) {
            Toast.makeText(this, "Trocar Senha", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId() == R.id.menu_garcom_sair_da_conta) {
            mAuth.signOut();
            Intent logout = new Intent(this, Login.class);
            startActivity(logout);
        }
        return super.onOptionsItemSelected(item);
    }
}
