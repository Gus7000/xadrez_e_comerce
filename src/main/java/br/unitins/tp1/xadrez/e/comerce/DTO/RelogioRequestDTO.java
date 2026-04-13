package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record RelogioRequestDTO(
    @NotBlank(message = "O modelo não pode estar em branco")
    String modelo,
    @NotBlank(message = "As dimensões não podem estar em branco")
    String dimensoes,
    @NotNull(message = "O fabricante é obrigatório")
    @Positive(message = "O id do fabricante deve ser positivo")
    Long fabricanteId
) {
}
