package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;

public record RelogioResponseDTO(
    Long id,
    String modelo,
    String dimensoes,
    Long fabricanteId,
    RelogioDigitalResponseDTO relogioDigital,
    RelogioAnalogicoResponseDTO relogioAnalogico,
    LocalDateTime dataCadastro
) {
}
