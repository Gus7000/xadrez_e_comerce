package br.unitins.tp1.xadrez.e.comerce.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.unitins.tp1.xadrez.e.comerce.DTO.CupomDescontoResponseDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cupom_desconto")
public class CupomDesconto extends DefaultEntity {

    @Enumerated(EnumType.STRING)
    @Column(length = 40, nullable = false)
    private TipoCupomDesconto tipo;

    @Column(length = 100, unique = true)
    private String codigo;

    private LocalDate dataValidade;

    private boolean ativo;

    private Integer usoMaximo;

    private Integer usosRealizados;

    private boolean porUsuario;

    private BigDecimal valor;

    public TipoCupomDesconto getTipo() {
        return tipo;
    }

    public void setTipo(TipoCupomDesconto tipo) {
        this.tipo = tipo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Integer getUsoMaximo() {
        return usoMaximo;
    }

    public void setUsoMaximo(Integer usoMaximo) {
        this.usoMaximo = usoMaximo;
    }

    public Integer getUsosRealizados() {
        return usosRealizados;
    }

    public void setUsosRealizados(Integer usosRealizados) {
        this.usosRealizados = usosRealizados;
    }

    public boolean isPorUsuario() {
        return porUsuario;
    }

    public void setPorUsuario(boolean porUsuario) {
        this.porUsuario = porUsuario;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal calcularDesconto(BigDecimal subtotal) {
        if (subtotal == null || tipo == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal valorBase = valor != null ? valor : BigDecimal.ZERO;

        return switch (tipo) {
            case FIXO -> valorBase.min(subtotal);
            case PERCENTUAL -> subtotal.multiply(valorBase).divide(BigDecimal.valueOf(100)).min(subtotal);
            case FRETEGRATIS -> BigDecimal.ZERO;
        };
    }

    public CupomDescontoResponseDTO toDTO() {
        return new CupomDescontoResponseDTO(
                this.getId(),
                this.getCodigo(),
                this.getTipo(),
                this.getDataValidade(),
                this.isAtivo(),
                this.getUsoMaximo(),
                this.getUsosRealizados(),
                this.isPorUsuario(),
                this.getValor(),
                this.getDataCadastro());
    }
}
