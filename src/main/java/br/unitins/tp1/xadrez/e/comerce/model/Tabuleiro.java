package br.unitins.tp1.xadrez.e.comerce.model;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import br.unitins.tp1.xadrez.e.comerce.converter.CorPecaConverter;

@Entity
public class Tabuleiro extends DefaultEntity {
    private String tamanho;
    
    @Convert(converter = CorPecaConverter.class)
    private CorPeca cor;

    @ManyToOne
    @JoinColumn(name = "id_material")
    private Material material;

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public CorPeca getCor() {
        return cor;
    }

    public void setCor(CorPeca cor) {
        this.cor = cor;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    

}
