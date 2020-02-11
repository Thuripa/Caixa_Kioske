package com.example.caixakioske.Modelos;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caixakioske.R;

public class GavetaOrdem extends RecyclerView.ViewHolder {

    public TextView tvQuantidade;
    public TextView tvNomeProduto;
    public TextView tvPrecoTotal;

    public GavetaOrdem(@NonNull View itemView) {
        super(itemView);

        this.tvQuantidade = itemView.findViewById(R.id.tvQuantidadeOrdem);
        this.tvNomeProduto = itemView.findViewById(R.id.tvNomeProdutoOrdem);
        this.tvPrecoTotal = itemView.findViewById(R.id.tvPrecoTotalOrdem);


    }

}
