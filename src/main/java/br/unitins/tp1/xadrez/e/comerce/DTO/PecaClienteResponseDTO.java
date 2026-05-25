package br.unitins.tp1.xadrez.e.comerce.DTO;

public record PecaClienteResponseDTO(
    Long id,
    String cor,
    String tipo,
    MaterialResponseDTO material,
    double diametroCm,
    double alturaCm
) {
}