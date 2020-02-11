package com.example.caixakioske;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.caixakioske.Modelos.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DadosUsuario extends AppCompatActivity {

    private FirebaseAuth mAuth;


    private TextView tvEmailDados;
    private TextView tvAcessoDados;
    private Button btnVoltar;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_usuario);

        tvEmailDados = findViewById(R.id.tvEmailDados);
        tvAcessoDados = findViewById(R.id.tvAcessoDados);

        // Instancia FireBase Auth
        mAuth = FirebaseAuth.getInstance();
        // Pega Usuario Atual
        FirebaseUser usuario = mAuth.getCurrentUser();

        Intent intent = getIntent();

        if(getIntent() != null) {
            this.usuario = intent.getParcelableExtra("usuario");
            if(this.usuario != null) {
                boolean admin = this.usuario.isAdmin();
                if(admin) {
                    tvAcessoDados.setText("Acesso: Administrador");
                } else {
                    tvAcessoDados.setText("Acesso: Garçom");
                }
            }
        }

        if(usuario != null) {

            tvEmailDados.setText("Email: "+usuario.getEmail());

        } else {
            tvEmailDados.setText("Email: NULL");
        }

    }
}
