package br.unitins.tp1.xadrez.e.comerce.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class ComponenteFisico extends DefaultEntity {
    
    @ManyToOne
    @JoinColumn(name = "id_material")
    private Material material;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
