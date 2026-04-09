package br.unitins.tp1.xadrez.e.comerce.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class JogoCompleto extends DefaultEntity {
    private String nome;
    private double preco;
    private String descricao;
    @Column(name = "estoque_disponivel" )
    private int estoqueDisponivel;
    
    @ManyToOne
    @JoinColumn(name = "id_kit_peca")
    private KitPeca kitPeca;
    
    @ManyToOne
    @JoinColumn(name = "id_tabuleiro")
    private Tabuleiro tabuleiro;

    @ManyToOne
    @JoinColumn(name = "id_fabricante")
    private Fabricante fabricante;

    @ManyToOne
    @JoinColumn(name = "id_relogio")
    private Relogio relogio;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getEstoqueDisponivel() {
        return estoqueDisponivel;
    }
    public void setEstoqueDisponivel(int estoqueDisponivel) {
        this.estoqueDisponivel = estoqueDisponivel;
    }
    public KitPeca getKitPeca() {
        return kitPeca;
    }
    public void setKitPeca(KitPeca kitPeca) {
        this.kitPeca = kitPeca;
    }
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
    public void setTabuleiro(Tabuleiro tabuleiro) {
        this.tabuleiro = tabuleiro;
    }
    public Fabricante getFabricante() {
        return fabricante;
    }
    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }
    public Relogio getRelogio() {
        return relogio;
    }
    public void setRelogio(Relogio relogio) {
        this.relogio = relogio;
    }
}
