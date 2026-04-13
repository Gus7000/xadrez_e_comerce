package br.unitins.tp1.xadrez.e.comerce.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "relogio_digital")
public class RelogioDigital extends Relogio {
    
    @Column(name = "incremento")
    private Integer incremento;
    @Column(name = "modo_tempo")
    private String modoTempo;
    @Column(name = "display_duplo")
    private Boolean displayDuplo;
    @Column(name = "tem_buzzer")
    private Boolean temBuzzer;

    public Integer getIncremenento() {
        return incremento;
    }

    public void setIncremenento(Integer incremento) {
        this.incremento = incremento;
    }

    public String getModoTempo() {
        return modoTempo;
    }

    public void setModoTempo(String modoTempo) {
        this.modoTempo = modoTempo;
    }

    public Boolean getDisplayDuplo() {
        return displayDuplo;
    }

    public void setDisplayDuplo(Boolean displayDuplo) {
        this.displayDuplo = displayDuplo;
    }

    public Boolean getTemBuzzer() {
        return temBuzzer;
    }

    public void setTemBuzzer(Boolean temBuzzer) {
        this.temBuzzer = temBuzzer;
    }
}
