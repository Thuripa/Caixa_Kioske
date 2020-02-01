package com.example.caixakioske.Modelos;

public class Mesa {

    public String nome;
    private Float valorMesa;


    public Mesa(){

    }

    public Mesa(String nome, Float valorMesa) {
        this.nome = nome;
        this.valorMesa = valorMesa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getValorMesa() {
        return valorMesa;
    }

    public void setValorMesa(Float valorMesa) {
        this.valorMesa = valorMesa;
    }
}
