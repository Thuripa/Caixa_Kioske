package com.example.caixakioske.Modelos;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caixakioske.R;

public class GavetaMesa extends RecyclerView.ViewHolder {

    public TextView tvNomeMesa;
    public TextView tvValorMesa;

    public GavetaMesa(@NonNull View itemView) {

        super(itemView);

        this.tvNomeMesa = itemView.findViewById(R.id.tvNomeMesa);
        this.tvValorMesa = itemView.findViewById(R.id.tvValorMesa);

    }
}
