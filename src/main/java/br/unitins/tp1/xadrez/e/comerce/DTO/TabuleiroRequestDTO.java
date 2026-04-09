package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TabuleiroRequestDTO(
    @NotBlank(message = "O tamanho não pode estar em branco")
    @Size(min = 2, max = 50, message = "O tamanho deve conter entre 2 a 50 caracteres")
    String tamanho,
    @NotNull(message = "O material é obrigatório")
    @Positive(message = "O id do material deve ser positivo")
    Long materialId
) {
}
