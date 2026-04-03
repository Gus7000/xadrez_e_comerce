package br.unitins.tp1.xadrez.e.comerce.model;

import jakarta.persistence.Entity;

@Entity
public class Material extends DefaultEntity{
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
