package br.unitins.tp1.xadrez.e.comerce.DTO;

public record JogoXadrezClienteResponseDTO(
    Long id,
    String nome,
    double preco,
    String descricao,
    KitPecaClienteResponseDTO kitPeca,
    TabuleiroClienteResponseDTO tabuleiro,
    RelogioClienteResponseDTO relogio
) {
}