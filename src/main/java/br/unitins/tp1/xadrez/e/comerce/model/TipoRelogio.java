package br.unitins.tp1.xadrez.e.comerce.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum TipoRelogio {
    ANALOGICO(1L,"Analógico"),
    DIGITAL(2L, "Digital");

    private final Long ID;
    private final String NOME;

    TipoRelogio(Long id, String nome){
        this.ID = id;
        this.NOME = nome;
    }

    public Long getID() {
        return ID;
    }

    public String getNOME() {
        return NOME;
    }
    
    public static TipoRelogio valueOf(Long id){
        for (TipoRelogio tipo : TipoRelogio.values()){
            if(tipo.getID().equals(id))
                return tipo;
        }
        return null;
    }
}
