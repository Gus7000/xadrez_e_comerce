package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;

import br.unitins.tp1.xadrez.e.comerce.model.TipoRelogio;

public record RelogioResponseDTO(
    Long id,
    String modelo,
    TipoRelogio tipo,
    LocalDateTime dataCadastro
) {
}
