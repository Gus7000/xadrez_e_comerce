package br.unitins.tp1.xadrez.e.comerce.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.unitins.tp1.xadrez.e.comerce.model.TipoCupomDesconto;
import jakarta.validation.constraints.NotBlank;

public record CupomDescontoRequestDTO(
    @NotBlank(message = "O codigo é obrigatório")
    String codigo,
    TipoCupomDesconto tipo,
    LocalDate dataValidade,
    Boolean ativo,
    Integer usoMaximo,
    Boolean porUsuario,
    BigDecimal valor
) {
}
