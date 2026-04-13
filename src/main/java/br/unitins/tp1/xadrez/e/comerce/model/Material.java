package br.unitins.tp1.xadrez.e.comerce.model;

import jakarta.persistence.Entity;

@Entity
public class Material extends DefaultEntity{
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
