package com.example.caixakioske.TelasCadastros;

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
import com.example.caixakioske.Modelos.Usuario;
import com.example.caixakioske.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroUsuario extends AppCompatActivity {


    Button btnRegistrar;
    EditText etEmailReg;
    EditText etSenhaReg;
    Usuario usuario;

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
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.d("ALCM", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastroUsuario.this, "Erro no Cadastro!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser currentUser) {

        if(currentUser != null) {
            Intent intent = new Intent(this, Login.class);
            intent.putExtra("caminho", "cadastroUsuario");
            startActivity(intent);
        }

    }

}