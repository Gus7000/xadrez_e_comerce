package br.unitins.tp1.xadrez.e.comerce.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "cupom_desconto")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
public abstract class CupomDesconto extends DefaultEntity {

    @Column(length = 100, unique = true)
    private String codigo;

    private LocalDate dataValidade;

    private boolean ativo;

    private Integer usoMaximo;

    private Integer usosRealizados;

    private boolean porUsuario;

    private LocalDate expiraEm;

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

    public LocalDate getExpiraEm() {
        return expiraEm;
    }

    public void setExpiraEm(LocalDate expiraEm) {
        this.expiraEm = expiraEm;
    }
}
