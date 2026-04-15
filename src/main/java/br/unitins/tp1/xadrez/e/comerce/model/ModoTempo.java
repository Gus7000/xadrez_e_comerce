package br.unitins.tp1.xadrez.e.comerce.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

@JsonFormat(shape = Shape.OBJECT)
public enum ModoTempo {
    FISCHER(1L, "Fischer"),
    DELAY(2L, "Delay"),
    BRONSTEIN(3L, "Bronstein"),
    SIMPLES(4L, "Simples");

    private final Long ID;
    private final String NOME;

    ModoTempo(Long id, String nome) {
        this.ID = id;
        this.NOME = nome;
    }

    public Long getId() {
        return ID;
    }

    public String getNome() {
        return NOME;
    }

    public static ModoTempo valueOf(Long id) {
        for (ModoTempo modo : ModoTempo.values()) {
            if (modo.getId().equals(id))
                return modo;
        }
        throw new IllegalArgumentException("Modo de tempo inválido: " + id);
    }
}
