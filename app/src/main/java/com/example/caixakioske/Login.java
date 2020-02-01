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

import com.example.caixakioske.Telas_Cadastros.CadastroUsuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText etEmail;
    private EditText etSenha;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etSenha = findViewById(R.id.etSenha);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        if (getIntent().getStringExtra("nome") != null) {
            etEmail.setText(getIntent().getStringExtra("nome"));
        }

        TextView tvCriarUmaNovaConta = findViewById(R.id.tvCriarUmaNovaConta);

        tvCriarUmaNovaConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, CadastroUsuario.class);
                startActivity(intent);
            }
        });

        Button btnLogin = findViewById(R.id.btnLogin);

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
                if(currentUser.getEmail().equals("tainhabichoesperto@gmail.com")){
                    Intent admin = new Intent(this, PainelAdmin.class);
                    String nome = currentUser.getDisplayName();
                    admin.putExtra("nome", nome);
                    startActivity(admin);
                }
            } else {
                Intent garcom = new Intent(this, PainelGarcom.class);
                String nome = currentUser.getDisplayName();
                garcom.putExtra("nome", nome);
                startActivity(garcom);
            }

        }

    }

    private void entrar(String email, String senha) {
        mAuth.signInWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("ALCM", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("ALCM", "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void login(boolean admin) {
        if(admin) {
            Intent intent = new Intent(this, PainelAdmin.class);
            intent.putExtra("nome", etEmail.getText().toString());
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, PainelGarcom.class);
            intent.putExtra("nome", etEmail.getText().toString());
            startActivity(intent);
        }




    }
}
