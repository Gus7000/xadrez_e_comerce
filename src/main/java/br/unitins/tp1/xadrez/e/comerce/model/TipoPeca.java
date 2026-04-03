package br.unitins.tp1.xadrez.e.comerce.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum TipoPeca {
    REI(1L,"Rei"),
    RAINHA(2L, "Rainha"),
    TORRE(3L,"Torre"),
    BISPO(4L, "Bispo"),
    CAVALO(5L,"Cavalo"),
    PEAO(6L, "Peao");

    private final Long ID;
    private final String NOME;

    TipoPeca(Long id, String nome){
        this.ID = id;
        this.NOME = nome;
    }

    public Long getId() {
        return ID;
    }

    public String getNome() {
        return NOME;
    }
    
    public static TipoPeca valueOf(Long id){
        for (TipoPeca tipo : TipoPeca.values()){
            if(tipo.getId().equals(id))
                return tipo;
        }
        throw new IllegalArgumentException("TipoRelogio inválido: " + id);
    }

}
