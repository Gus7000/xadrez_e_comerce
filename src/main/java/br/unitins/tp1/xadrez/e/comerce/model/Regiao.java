package br.unitins.tp1.xadrez.e.comerce.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum Regiao {
    NORTE(1L, "Norte"),
    SUL(2L, "Sul"),
    SUDESTE(3L, "Sudeste"),
    CENTRO_OESTE(4L, "Centro-Oeste"),
    NORDESTE(5L, "Nordeste");

    private final Long ID;
    private final String NOME;

    Regiao(Long id, String nome) {
        this.ID = id;
        this.NOME = nome;
    }
    public String getNome() {
        return NOME;
    }
    public Long getId() {
        return ID;
    }
    public static Regiao valueOf(Long id){
        for(Regiao regiao : Regiao.values()){
            if(regiao.getId().equals(id))
                return regiao;
        }
        return null;
    }
}
