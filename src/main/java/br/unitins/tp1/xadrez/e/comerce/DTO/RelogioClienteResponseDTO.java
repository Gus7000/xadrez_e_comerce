package br.unitins.tp1.xadrez.e.comerce.DTO;

public record RelogioClienteResponseDTO(
    Long id,
    String modelo,
    String dimensoes,
    FabricanteClienteResponseDTO fabricante,
    RelogioDigitalClienteResponseDTO relogioDigital,
    RelogioAnalogicoClienteResponseDTO relogioAnalogico
) {
}