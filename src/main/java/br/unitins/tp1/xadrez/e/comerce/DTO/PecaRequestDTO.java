package br.unitins.tp1.xadrez.e.comerce.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PecaRequestDTO(
    @NotNull(message = "A cor não pode ser nula")
    @Positive(message = "O id da cor deve ser positivo")
    Long corId,
    @NotNull(message = "O tipo não pode ser nulo")
    @Positive(message = "O id do tipo deve ser positivo")
    Long tipoId,
    @NotNull(message = "O material é obrigatório")
    @Positive(message = "O id do material deve ser positivo")
    Long materialId,
    @NotNull(message = "O diâmetro não pode ser nulo")
    @Positive(message = "O diâmetro deve ser positivo")
    double diametroCm,
    @NotNull(message = "A altura não pode ser nula")
    @Positive(message = "A altura deve ser positiva")
    double alturaCm
) {
}
