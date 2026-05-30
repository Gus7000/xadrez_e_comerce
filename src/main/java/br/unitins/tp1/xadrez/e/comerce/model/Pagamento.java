package br.unitins.tp1.xadrez.e.comerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagamento")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Pagamento extends DefaultEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private PagamentoStatus status;

    @Column(length = 200)
    private String identificadorTransacao;

    private BigDecimal valor;

    private LocalDateTime dataConfirmacao;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public PagamentoStatus getStatus() {
        return status;
    }

    public void setStatus(PagamentoStatus status) {
        this.status = status;
    }

    public String getIdentificadorTransacao() {
        return identificadorTransacao;
    }

    public void setIdentificadorTransacao(String identificadorTransacao) {
        this.identificadorTransacao = identificadorTransacao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(LocalDateTime dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
