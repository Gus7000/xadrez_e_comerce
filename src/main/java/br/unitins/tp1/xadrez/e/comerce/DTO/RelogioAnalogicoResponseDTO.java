package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.time.LocalDateTime;

import br.unitins.tp1.xadrez.e.comerce.model.Mecanismo;

public record RelogioAnalogicoResponseDTO(
    Long id,
    String modelo,
    String dimensoes,
    Boolean temBandeira,
    Boolean necessitaPilha,
    Mecanismo mecanismo,
    Long fabricanteId,
    LocalDateTime dataCadastro
) {
}
