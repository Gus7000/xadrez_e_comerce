package br.unitins.tp1.xadrez.e.comerce.model;

import jakarta.persistence.Entity;

@Entity
public class Tabuleiro extends ComponenteFisico {
    private String tamanho;

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
}
