package com.example.caixakioske.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Ordem implements Parcelable {

    Produto produto;
    int quantidade;
    Float valorTotal;
    String opcional;

    public Ordem() {

    }

    public Ordem(Produto produto, int quantidade, Float valorTotal) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setOpcional(String opcional) {
        this.opcional = opcional;
    }

    public String getOpcional(){
        return opcional;
    }


    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Float getPrecoTotal() {
        if(produto != null && quantidade != 0) {
            return produto.getPreco() * quantidade;
        } else {
            return 0f;
        }
    }

    protected Ordem(Parcel in) {
        produto = (Produto) in.readValue(Produto.class.getClassLoader());
        quantidade = in.readInt();
        opcional = in.readString();
        valorTotal = in.readByte() == 0x00 ? null : in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(produto);
        dest.writeInt(quantidade);
        dest.writeString(opcional);
        if (valorTotal == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeFloat(valorTotal);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ordem> CREATOR = new Parcelable.Creator<Ordem>() {
        @Override
        public Ordem createFromParcel(Parcel in) {
            return new Ordem(in);
        }

        @Override
        public Ordem[] newArray(int size) {
            return new Ordem[size];
        }
    };
}
