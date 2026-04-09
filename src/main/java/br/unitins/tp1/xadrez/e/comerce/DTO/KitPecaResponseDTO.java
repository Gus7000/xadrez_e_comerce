package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record KitPecaResponseDTO(
    Long id,
    List<ItemKitResponseDTO> itens,
    LocalDateTime dataCadastro
) {
}
