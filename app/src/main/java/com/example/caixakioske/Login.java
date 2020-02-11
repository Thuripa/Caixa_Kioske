package com.example.caixakioske;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caixakioske.Modelos.Usuario;
import com.example.caixakioske.TelasCadastros.CadastroUsuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText etEmail;
    private EditText etSenha;
    private FirebaseAuth mAuth;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);
        TextView tvCriarUmaNovaConta = findViewById(R.id.tvCriarUmaNovaConta);
        Button btnLogin = findViewById(R.id.btnLogin);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        intent = getIntent();

        if(intent.getStringExtra("caminho") != null) {
            if(intent.getStringExtra("caminho").equals("cadastroUsuario")) {
                etEmail.setText(mAuth.getCurrentUser().getEmail());
            }
        }



        tvCriarUmaNovaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, CadastroUsuario.class);
                startActivity(intent);
            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();

                entrar(email, senha);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

        if(currentUser != null) {
            if(currentUser.getEmail() != null){
                if(currentUser.getEmail().equals("tainhabichoesperto@gmail.com")
                        || currentUser.getEmail().equals("gabrielps41308@outlook.com")){

                    Intent admin = new Intent(this, PainelAdmin.class);
                    admin.putExtra("caminho", "login");
                    startActivity(admin);

                } else {
                    Intent garcom = new Intent(this, PainelGarcom.class);
                    garcom.putExtra("caminho", "login");
                    startActivity(garcom);
                }
            }

        }

    }

    private void entrar(String email, String senha) {
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            Toast.makeText(Login.this, "Usuario Incorreto",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
