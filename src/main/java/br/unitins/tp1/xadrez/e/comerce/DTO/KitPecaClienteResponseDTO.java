package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.util.List;

public record KitPecaClienteResponseDTO(
    Long id,
    List<ItemKitClienteResponseDTO> itens,
    FabricanteClienteResponseDTO fabricante
) {
}