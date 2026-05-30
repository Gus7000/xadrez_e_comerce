package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.unitins.tp1.xadrez.e.comerce.model.TipoCupomDesconto;

public record CupomDescontoResponseDTO(
    Long id,
    String codigo,
    TipoCupomDesconto tipo,
    LocalDate dataValidade,
    Boolean ativo,
    Integer usoMaximo,
    Integer usosRealizados,
    Boolean porUsuario,
    BigDecimal valor,
    LocalDateTime dataCadastro
) {
}
