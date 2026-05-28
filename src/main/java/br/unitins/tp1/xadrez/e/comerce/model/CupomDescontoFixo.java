package br.unitins.tp1.xadrez.e.comerce.model;

import java.math.BigDecimal;

import br.unitins.tp1.xadrez.e.comerce.DTO.CupomDescontoResponseDTO;
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

    @Override
    public BigDecimal calcularDesconto(BigDecimal subtotal) {
        BigDecimal valor = this.valorDesconto != null ? this.valorDesconto : BigDecimal.ZERO;
        if (subtotal == null) {
            return BigDecimal.ZERO;
        }
        return valor.min(subtotal);
    }

    @Override
    public CupomDescontoResponseDTO toDTO() {
        return new CupomDescontoResponseDTO(
            this.getId(),
            this.getCodigo(),
            "FIXO",
            this.getDataValidade(),
            this.isAtivo(),
            this.getUsoMaximo(),
            this.getUsosRealizados(),
            this.isPorUsuario(),
            null,
            this.getValorDesconto(),
            this.getDataCadastro()
        );
    }
}
