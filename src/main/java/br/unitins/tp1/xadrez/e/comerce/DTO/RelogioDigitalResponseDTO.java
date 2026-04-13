package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;

public record RelogioDigitalResponseDTO(
    Long id,
    String modelo,
    String dimensoes,
    Integer incremento,
    Boolean displayDuplo,
    Boolean temBuzzer,
    String modoTempo,
    Long fabricanteId,
    LocalDateTime dataCadastro
) {
}
