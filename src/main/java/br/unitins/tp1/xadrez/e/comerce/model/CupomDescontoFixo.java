package br.unitins.tp1.xadrez.e.comerce.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FIXO")
public class CupomDescontoFixo extends CupomDesconto {

    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }
}
