package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record JogoCompletoResponseDTO(
    Long id,
    String nome,
    double preco,
    String descricao,
    Long fabricanteId,
    String fabricanteNome,
    Long kitPecaId,
    List<ItemKitResponseDTO> itens,
    Long tabuleiroId,
    String tamanhTabuleiro,
    String corTabuleiro,
    LocalDateTime dataCadastro
) {
}
