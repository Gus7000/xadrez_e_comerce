package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CupomDescontoRequestDTO(
    String codigo,
    String tipo, // FIXO or PERCENTUAL
    LocalDate dataValidade,
    Boolean ativo,
    Integer usoMaximo,
    Boolean porUsuario,
    LocalDate expiraEm,
    Double percentualDesconto,
    BigDecimal valorDesconto
) {
}
