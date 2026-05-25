package br.unitins.tp1.xadrez.e.comerce.DTO;

import br.unitins.tp1.xadrez.e.comerce.model.Mecanismo;

public record RelogioAnalogicoClienteResponseDTO(
    Long id,
    String modelo,
    String dimensoes,
    Boolean temBandeira,
    Boolean necessitaPilha,
    Mecanismo mecanismo,
    FabricanteClienteResponseDTO fabricante
) {
}