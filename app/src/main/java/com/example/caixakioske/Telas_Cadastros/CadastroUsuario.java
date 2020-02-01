package com.example.caixakioske.Telas_Cadastros;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.caixakioske.Login;
import com.example.caixakioske.R;
import com.example.caixakioske.Modelos.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CadastroUsuario extends AppCompatActivity {


    Button btnRegistrar;
    EditText etEmailReg;
    EditText etSenhaReg;

    private FirebaseAuth mAuth;
    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        reff = FirebaseDatabase.getInstance().getReference("usuarios");

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        this.etEmailReg = findViewById(R.id.etEmailReg);
        this.etSenhaReg = findViewById(R.id.etSenhaReg);
        this.btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etEmailReg.getText() != null && etSenhaReg.getText() != null) {
                    //registrar(etEmailReg.getText().toString(), etSenhaReg.getText().toString());
                    criaConta(etEmailReg.getText().toString(), etSenhaReg.getText().toString());
                }
            }
        });
    }

    public void criaConta(String email, String senha) {
        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("ALCM", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("ALCM", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastroUsuario.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser) {

        if(currentUser != null) {
            Intent intent = new Intent(this, Login.class);
            intent.putExtra("email", currentUser.getEmail());
        } else {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }

    }

    public void registrar(String nome, String senha) {

        if (nome != null && senha != null) {

           Usuario usuario = new Usuario(nome, senha, false);
           reff.push().setValue(usuario);
            Toast.makeText(this, "Usuario Cadastrado", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, Login.class);
            intent.putExtra("nome", usuario.getNome());
            startActivity(intent);
        } else {
            Toast.makeText(this, "Insira Nome e Senha", Toast.LENGTH_SHORT).show();
        }

    }

}