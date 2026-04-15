package br.unitins.tp1.xadrez.e.comerce.model;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "relogio_digital")
public class RelogioDigital extends Relogio {
    
    @Column(name = "incremento")
    private Integer incremento;
    
    @ElementCollection(targetClass = ModoTempo.class)
    @CollectionTable(
        name = "relogio_digital_modo_tempo",
        joinColumns = @JoinColumn(name = "relogio_digital_id")
    )
    @Column(name = "modo_tempo")
    @Enumerated(EnumType.STRING)
    private Set<ModoTempo> modoTempo = new HashSet<>();
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

    public Set<ModoTempo> getModoTempo() {
        return modoTempo;
    }

    public void setModoTempo(Set<ModoTempo> modoTempo) {
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
