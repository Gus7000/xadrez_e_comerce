package br.unitins.tp1.xadrez.e.comerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Relogio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelo;
    private TipoRelogio tipo;

    
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public TipoRelogio getTipo() {
        return tipo;
    }
    public void setTipo(TipoRelogio tipo) {
        this.tipo = tipo;
    }
    public Long getId() {
        return id;
    }
    
}
