package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.NotNull;

public record MeListaDesejosItemRequestDTO(
        @NotNull
        Long jogoId
) {
}
