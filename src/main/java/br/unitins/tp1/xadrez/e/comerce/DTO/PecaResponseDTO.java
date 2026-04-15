package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;

public record PecaResponseDTO(
    Long id,
    String cor,
    String tipo,
    MaterialResponseDTO material,
    double diametroCm,
    double alturaCm,
    LocalDateTime dataCadastro
) {
}
