package com.example.caixakioske.Modelos;

public class Usuario {

    private String nome, senha;
    private boolean admin;

    public Usuario() {}

    public Usuario(String nome, String senha, boolean admin) {

        this.nome = nome;
        this.senha = senha;
        this.admin = admin;

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
