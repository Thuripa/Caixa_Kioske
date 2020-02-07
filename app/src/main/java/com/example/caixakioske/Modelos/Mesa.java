package com.example.caixakioske.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Mesa implements Parcelable {

    public String id;
    private String idAtendente;
    public String nome;
    private Float valorMesa;


    public Mesa(){

    }

    public Mesa(String id, String idAtendente, String nome, Float valorMesa) {
        this.id = id;
        this.idAtendente = idAtendente;
        this.nome = nome;
        this.valorMesa = valorMesa;
    }


    protected Mesa(Parcel in) {
        id = in.readString();
        idAtendente = in.readString();
        nome = in.readString();
        if (in.readByte() == 0) {
            valorMesa = null;
        } else {
            valorMesa = in.readFloat();
        }
    }

    public static final Creator<Mesa> CREATOR = new Creator<Mesa>() {
        @Override
        public Mesa createFromParcel(Parcel in) {
            return new Mesa(in);
        }

        @Override
        public Mesa[] newArray(int size) {
            return new Mesa[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getIdAtendente() {
        return idAtendente;
    }

    public String getNome() {
        return nome;
    }

    public Float getValorMesa() {
        return valorMesa;
    }



    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setIdAtendente(String idAtendente) {
        this.idAtendente = idAtendente;
    }

    public void setValorMesa(Float valorMesa) {
        this.valorMesa = valorMesa;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(idAtendente);
        dest.writeString(nome);
        dest.writeFloat(valorMesa);
    }
}
