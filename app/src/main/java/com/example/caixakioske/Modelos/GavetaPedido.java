package com.example.caixakioske.Modelos;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caixakioske.R;

public class GavetaPedido extends RecyclerView.ViewHolder {

    public TextView tvNomeProduto;

    public GavetaPedido(@NonNull View itemView) {
        super(itemView);

        this.tvNomeProduto = itemView.findViewById(R.id.tvPedidoMesa);

    }

}
