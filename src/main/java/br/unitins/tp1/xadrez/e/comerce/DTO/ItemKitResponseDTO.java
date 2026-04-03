package br.unitins.tp1.xadrez.e.comerce.DTO;

public record ItemKitResponseDTO(
    Long id,
    Long pecaId,
    String pecaTipo,
    String pecaMaterial,
    String pecaCor,
    Integer quantidade
) {
}
