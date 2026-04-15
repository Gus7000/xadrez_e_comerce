package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;


public record JogoXadrezResponseDTO(
    Long id,
    String nome,
    double preco,
    String descricao,
    int estoqueDisponivel,
    KitPecaResponseDTO kitPeca,
    TabuleiroResponseDTO tabuleiro,
    RelogioResponseDTO relogio,
    LocalDateTime dataCadastro
) {
}
