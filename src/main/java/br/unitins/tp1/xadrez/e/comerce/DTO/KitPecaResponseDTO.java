package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record KitPecaResponseDTO(
    Long id,
    String nome,
    double preco,
    String descricao,
    Long fabricanteId,
    String fabricanteNome,
    List<ItemKitResponseDTO> itens,
    LocalDateTime dataCadastro
) {
}
