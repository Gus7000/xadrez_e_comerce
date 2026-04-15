package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;

public record TabuleiroResponseDTO(
    Long id,
    String tamanho,
    MaterialResponseDTO material,
    FabricanteResponseDTO fabricante,
    LocalDateTime dataCadastro
) {
}
