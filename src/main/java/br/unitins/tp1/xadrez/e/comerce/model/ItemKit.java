package br.unitins.tp1.xadrez.e.comerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemKit extends DefaultEntity {

    @ManyToOne
    @JoinColumn(name = "kit_id")
    private KitPeca kit;

    @ManyToOne
    @JoinColumn(name = "peca_id")
    private Peca peca;

    private int quantidade;

    public KitPeca getKit() {
        return kit;
    }

    public void setKit(KitPeca kit) {
        this.kit = kit;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    
}
