package com.example.caixakioske.Modelos;

public class Produto {

    private String nome, tipo;
    private float preco;

    public Produto(){}

    public Produto(String nome, String tipo, float preco) {
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
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
}
