package br.unitins.tp1.xadrez.e.comerce.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class JogoCompleto extends DefaultEntity{
    @ManyToOne
    @JoinColumn(name = "id_kit_peca")
    private KitPeca kitPeca;
    
    @ManyToOne
    @JoinColumn(name = "id_tabuleiro")
    private Tabuleiro tabuleiro;

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

    

}
