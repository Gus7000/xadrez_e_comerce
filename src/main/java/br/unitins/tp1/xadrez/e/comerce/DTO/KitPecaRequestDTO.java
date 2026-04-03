package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.util.List;

import jakarta.validation.constraints.NotNull;

public record KitPecaRequestDTO(
    @NotNull(message = "A lista de itens não pode ser nula")
    List<ItemKitRequestDTO> itens
) {
}
