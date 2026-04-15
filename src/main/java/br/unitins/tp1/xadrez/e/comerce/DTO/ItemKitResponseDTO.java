package br.unitins.tp1.xadrez.e.comerce.DTO;

public record ItemKitResponseDTO(
    Long id,
    PecaResponseDTO peca,
    Integer quantidade
) {
}
