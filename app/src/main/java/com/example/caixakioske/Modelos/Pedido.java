package com.example.caixakioske.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class Pedido implements Parcelable {

    private String nomeGarcom;
    private ArrayList<Produto> produtos;
    private ArrayList<Integer> quantidades;
    private Date data;


    public Pedido() {

    }

    public Pedido(String nomeGarcom, ArrayList<Produto> produtos, Date data) {

        this.nomeGarcom = nomeGarcom;
        this.data = data;
        this.produtos = produtos;

    }

    // GETTERS E SETTERS

    public String getNomeGarcom() {
        return nomeGarcom;
    }

    public void setNomeGarcom(String nomeGarcom) {
        this.nomeGarcom = nomeGarcom;
    }

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    private Pedido(Parcel in) {
        nomeGarcom = in.readString();
        if (in.readByte() == 0x01) {
            produtos = new ArrayList<>();
            in.readList(produtos, Produto.class.getClassLoader());
        } else {
            produtos = null;
        }
        long tmpData = in.readLong();
        data = tmpData != -1 ? new Date(tmpData) : null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nomeGarcom);
        if (produtos == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(produtos);
        }
        dest.writeLong(data != null ? data.getTime() : -1L);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Pedido> CREATOR = new Parcelable.Creator<Pedido>() {
        @Override
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override
        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };
}
