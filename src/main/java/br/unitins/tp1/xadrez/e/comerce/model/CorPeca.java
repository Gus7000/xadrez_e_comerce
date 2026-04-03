package br.unitins.tp1.xadrez.e.comerce.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum CorPeca {
    PRETO(1L,"Preto"),
    BRANCO(2L, "Branco");

    private final Long ID;
    private final String NOME;

    CorPeca(Long id, String nome){
        this.ID = id;
        this.NOME = nome;
    }

    public Long getId() {
        return ID;
    }

    public String getNome() {
        return NOME;
    }
    
    public static CorPeca valueOf(Long id){
        for (CorPeca cor : CorPeca.values()){
            if(cor.getId().equals(id))
                return cor;
        }
        throw new IllegalArgumentException("CorPeca inválido: " + id);
    }
}
