package com.dev.ric.mensageiro.model;

import com.dev.ric.mensageiro.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;


public class Usuario {
    private String id;
    private String nome;
    private String email;
    private String senha;

    public Usuario() {

    }

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    @Exclude
    public String  getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void salvarDados(){
       DatabaseReference firebaseDatabase = ConfiguracaoFirebase.getFirebaseDB();
       firebaseDatabase.child("usuarios").child(this.getId()).setValue(this);
    }
}
