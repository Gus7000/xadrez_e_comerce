package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.NotNull;

public record JogoCompletoRequestDTO(
    @NotNull(message = "O kit de peças é obrigatório")
    Long kitPecaId,
    @NotNull(message = "O tabuleiro é obrigatório")
    Long tabuleiroId
) {
}
