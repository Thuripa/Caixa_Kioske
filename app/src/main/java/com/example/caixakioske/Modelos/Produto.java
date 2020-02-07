package com.example.caixakioske.Modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Produto implements Parcelable {

    private String id;
    private String nome;
    private String tipo;
    private float preco;

    public Produto(){}

    public Produto(String id,String nome, String tipo, float preco) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
    }

    protected Produto(Parcel in) {
        nome = in.readString();
        tipo = in.readString();
        preco = in.readFloat();
        id = in.readString();
    }



    // GETTERS
    public String getNome() {
        return nome;
    }
    public String getTipo() {
        return tipo;
    }
    public float getPreco() {
        return preco;
    }
    public String getId() { return id;}
    // SETTERS
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public void setPreco(float preco) {
        this.preco = preco;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(tipo);
        dest.writeFloat(preco);
        dest.writeString(id);
    }


    public static final Creator<Produto> CREATOR = new Creator<Produto>() {
        @Override
        public Produto createFromParcel(Parcel in) {
            return new Produto(in);
        }

        @Override
        public Produto[] newArray(int size) {
            return new Produto[size];
        }
    };

}
