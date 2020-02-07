package com.example.caixakioske.Modelos;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caixakioske.R;

public class GavetaPedido extends RecyclerView.ViewHolder {

    public TextView tvNomeProduto;
    public TextView tvprecoProduto;
    public TextView tvQuantidade;

    public GavetaPedido(@NonNull View itemView) {
        super(itemView);

        this.tvNomeProduto = itemView.findViewById(R.id.tvNomeProdutoPedido);
        this.tvprecoProduto = itemView.findViewById(R.id.tvValorProdutos);
        this.tvQuantidade = itemView.findViewById(R.id.tvQuantidade);

    }

}
