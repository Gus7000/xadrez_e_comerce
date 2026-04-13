package br.unitins.tp1.xadrez.e.comerce.model;

import br.unitins.tp1.xadrez.e.comerce.converter.CorPecaConverter;
import br.unitins.tp1.xadrez.e.comerce.converter.TipoPecaConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Peca extends DefaultEntity {
    
    @Column(name = "diametro_cm")
    private double diametroCm;
    @Column(name = "altura_cm")
    private double alturaCm;
    
    @Convert(converter = CorPecaConverter.class)
    private CorPeca cor;
    
    @Convert(converter = TipoPecaConverter.class)
    private TipoPeca tipo;
    
    @ManyToOne
    @JoinColumn(name = "id_material")
    private Material material;
    
    public double getDiametroCm() {
        return diametroCm;
    }

    public void setDiametroCm(double diametroCm) {
        this.diametroCm = diametroCm;
    }

    public double getAlturaCm() {
        return alturaCm;
    }

    public void setAlturaCm(double alturaCm) {
        this.alturaCm = alturaCm;
    }

    public CorPeca getCor() {
        return cor;
    }
    
    public void setCor(CorPeca cor) {
        this.cor = cor;
    }
    
    public TipoPeca getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoPeca tipo) {
        this.tipo = tipo;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
