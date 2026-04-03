package br.unitins.tp1.xadrez.e.comerce.model;


import br.unitins.tp1.xadrez.e.comerce.converter.TipoRelogioConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;

@Entity
public class Relogio extends DefaultEntity {
    
    private String modelo;
    @Convert(converter = TipoRelogioConverter.class)
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
   
    
}
