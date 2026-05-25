package br.unitins.tp1.xadrez.e.comerce.DTO;

public record ItemKitClienteResponseDTO(
    Long id,
    PecaClienteResponseDTO peca,
    Integer quantidade
) {
}