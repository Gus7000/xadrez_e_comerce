package br.unitins.tp1.xadrez.e.comerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Tabuleiro extends DefaultEntity {
    
    private String tamanho;
    
    @ManyToOne
    @JoinColumn(name = "id_material")
    private Material material;
    
    @ManyToOne
    @JoinColumn(name = "id_fabricante")
    private Fabricante fabricante;

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }
}
