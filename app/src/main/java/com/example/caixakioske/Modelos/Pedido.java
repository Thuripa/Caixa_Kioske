package com.example.caixakioske.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

public class Pedido implements Parcelable {

    private String id_pedido;
    private String atendente;
    private Date data;
    private ArrayList<Ordem> ordems;
    private boolean pronto;


    public Pedido() {

    }

    public Pedido(String id_pedido, String atendente, Date data, ArrayList<Ordem> ordems) {

        this.id_pedido = id_pedido;
        this.atendente = atendente;
        this.data = data;
        this.ordems = ordems;
        this.pronto = false;

    }

    // GETTERS E SETTERS

    public void setId_pedido(String  id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getId_pedido() {
        return id_pedido;
    }

    public String getAtendente() {
        return atendente;
    }

    public void setAtendente(String atendente) {
        this.atendente = atendente;
    }

    public Date getData() {
        return data;
    }

    public void setOrdems(ArrayList<Ordem> ordems) {
        this.ordems = ordems;
    }

    public ArrayList<Ordem> getOrdems() {
        return ordems;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setDataAgora() {
        this.data = new Date();
    }

    public void setPronto(boolean pronto) {
        this.pronto = pronto;
    }

    public boolean isPronto() {
        return pronto;
    }

    protected Pedido(Parcel in) {
        id_pedido = in.readString();
        atendente = in.readString();
        long tmpData = in.readLong();
        data = tmpData != -1 ? new Date(tmpData) : null;
        if (in.readByte() == 0x01) {
            ordems = new ArrayList<Ordem>();
            in.readList(ordems, Ordem.class.getClassLoader());
        } else {
            ordems = null;
        }
        pronto = in.readByte() != 0x00;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_pedido);
        dest.writeString(atendente);
        dest.writeLong(data != null ? data.getTime() : -1L);
        if (ordems == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ordems);
        }
        dest.writeByte((byte) (pronto ? 0x01 : 0x00));
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
