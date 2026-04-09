package br.unitins.tp1.xadrez.e.comerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Tabuleiro extends ComponenteFisico {
    private String tamanho;
    @ManyToOne
    @JoinColumn(name = "id_fabricante")
    private Fabricante fabricante;

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
    public Fabricante getFabricante() {
        return fabricante;
    }
    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }
}
