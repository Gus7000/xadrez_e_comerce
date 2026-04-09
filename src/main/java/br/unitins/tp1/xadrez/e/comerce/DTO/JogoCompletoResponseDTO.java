package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record JogoCompletoResponseDTO(
    Long id,
    String nome,
    double preco,
    String descricao,
    int estoqueDisponivel,
    Long kitPecaId,
    List<ItemKitResponseDTO> itens,
    Long tabuleiroId,
    String tabuleiroTamanho,
    Long relogioId,
    LocalDateTime dataCadastro
) {
}
