package com.example.caixakioske.Modelos;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.caixakioske.R;

public class GavetaProduto extends RecyclerView.ViewHolder {

    public TextView tvNomeProduto;
    public TextView tvPrecoProduto;

    public GavetaProduto(View v) {
        super(v);

        tvNomeProduto = v.findViewById(R.id.tvNomeProduto);
        tvPrecoProduto = v.findViewById(R.id.tvPrecoProduto);

    }

}
