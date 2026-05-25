package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.util.Set;

public record RelogioDigitalClienteResponseDTO(
    Long id,
    String modelo,
    String dimensoes,
    Integer incremento,
    Boolean displayDuplo,
    Boolean temBuzzer,
    Set<String> modoTempo,
    FabricanteClienteResponseDTO fabricante
) {
}