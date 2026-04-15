package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;
import java.util.Set;

public record RelogioDigitalResponseDTO(
    Long id,
    String modelo,
    String dimensoes,
    Integer incremento,
    Boolean displayDuplo,
    Boolean temBuzzer,
    Set<String> modoTempo,
    FabricanteResponseDTO fabricante,
    LocalDateTime dataCadastro
) {
}
