package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;

public record PecaResponseDTO(
    Long id,
    String cor,
    Long corId,
    String tipo,
    Long tipoId,
    Long materialId,
    String materialTipo,
    LocalDateTime dataCadastro
) {
}
