package br.unitins.tp1.xadrez.e.comerce.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum Mecanismo {
    CORDA(1L, "Corda"),
    QUARTZ(2L, "Quartz");

    private final Long ID;
    private final String NOME;

    Mecanismo(Long id, String nome) {
        this.ID = id;
        this.NOME = nome;
    }

    public Long getId() {
        return ID;
    }

    public String getNome() {
        return NOME;
    }

    public static Mecanismo valueOf(Long id) {
        for (Mecanismo mecanismo : Mecanismo.values()) {
            if (mecanismo.getId().equals(id))
                return mecanismo;
        }
        throw new IllegalArgumentException("Mecanismo inválido: " + id);
    }
}
