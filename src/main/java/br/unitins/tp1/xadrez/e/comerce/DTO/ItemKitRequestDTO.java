package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemKitRequestDTO(
    @NotNull(message = "ID da peça não pode ser nulo")
    Long pecaId,
    
    @NotNull(message = "Quantidade não pode ser nula")
    @Positive(message = "Quantidade deve ser maior que 0")
    Integer quantidade
) {
}
