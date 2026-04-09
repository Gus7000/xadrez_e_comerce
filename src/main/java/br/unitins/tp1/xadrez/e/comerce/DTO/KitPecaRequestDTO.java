package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record KitPecaRequestDTO(
    @NotNull(message = "A lista de itens não pode ser nula")
    List<ItemKitRequestDTO> itens,
    @NotNull(message = "O fabricante é obrigatório")
    @Positive(message = "O id do fabricante deve ser positivo")
    Long fabricanteId
) {
}
