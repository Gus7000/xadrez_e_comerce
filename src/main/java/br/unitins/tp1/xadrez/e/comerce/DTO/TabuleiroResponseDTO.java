package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;

public record TabuleiroResponseDTO(
    Long id,
    String tamanho,
    String cor,
    Long corId,
    Long materialId,
    String materialTipo,
    LocalDateTime dataCadastro
) {
}
