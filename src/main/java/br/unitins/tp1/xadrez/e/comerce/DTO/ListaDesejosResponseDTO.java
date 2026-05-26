package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;
import java.util.List;

public record ListaDesejosResponseDTO(
        Long id,
        Long usuarioId,
        String usuarioEmail,
        List<Long> jogoIds,
        LocalDateTime dataCadastro) {
}
