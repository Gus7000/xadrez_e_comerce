package br.unitins.tp1.xadrez.e.comerce.DTO;

import br.unitins.tp1.xadrez.e.comerce.model.TipoRelogio;

public record RelogioResponseDTO(
    Long id,
    String modelo,
    TipoRelogio tipo
) {
}
