package br.unitins.tp1.xadrez.e.comerce.model;

import java.math.BigDecimal;

import br.unitins.tp1.xadrez.e.comerce.DTO.CupomDescontoResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PERCENTUAL")
public class CupomDescontoPercentual extends CupomDesconto {

    @Column(name = "percentual_desconto")
    private Double percentualDesconto;

    public Double getPercentualDesconto() {
        return percentualDesconto;
    }

    public void setPercentualDesconto(Double percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }

    @Override
    public BigDecimal calcularDesconto(BigDecimal subtotal) {
        if (subtotal == null) {
            return BigDecimal.ZERO;
        }
        if (this.percentualDesconto == null || this.percentualDesconto <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal perc = BigDecimal.valueOf(this.percentualDesconto).divide(BigDecimal.valueOf(100));
        BigDecimal desconto = subtotal.multiply(perc);
        return desconto.min(subtotal);
    }

    @Override
    public CupomDescontoResponseDTO toDTO() {
        return new CupomDescontoResponseDTO(
            this.getId(),
            this.getCodigo(),
            "PERCENTUAL",
            this.getDataValidade(),
            this.isAtivo(),
            this.getUsoMaximo(),
            this.getUsosRealizados(),
            this.isPorUsuario(),
            this.getPercentualDesconto(),
            null,
            this.getDataCadastro()
        );
    }
}
