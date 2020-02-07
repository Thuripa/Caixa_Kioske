package com.example.caixakioske.TelasCadastros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.caixakioske.Modelos.Mesa;
import com.example.caixakioske.R;

public class EditarMesa extends AppCompatActivity {

    Mesa mesa;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mesa);
    }
}
