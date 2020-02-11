package com.example.caixakioske.Modelos;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.caixakioske.R;

public class GavetaPedido extends RecyclerView.ViewHolder {

    public TextView tvPedidoNum;
    public TextView tvEmailPedido;

    public GavetaPedido(@NonNull View itemView) {
        super(itemView);

        this.tvPedidoNum = itemView.findViewById(R.id.TvPedidoNum);
        this.tvEmailPedido = itemView.findViewById(R.id.tvEmailPedido);

    }

}
