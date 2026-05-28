package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CupomDescontoResponseDTO(
    Long id,
    String codigo,
    String tipo,
    LocalDate dataValidade,
    Boolean ativo,
    Integer usoMaximo,
    Integer usosRealizados,
    Boolean porUsuario,
    Double percentualDesconto,
    BigDecimal valorDesconto,
    LocalDateTime dataCadastro
) {
}
