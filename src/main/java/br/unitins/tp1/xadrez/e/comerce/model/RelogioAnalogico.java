package br.unitins.tp1.xadrez.e.comerce.model;

import br.unitins.tp1.xadrez.e.comerce.converter.MecanismoConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "relogio_analogico")
public class RelogioAnalogico extends Relogio {
    
    @Convert(converter = MecanismoConverter.class)
    private Mecanismo mecanismo;
    @Column(name = "tem_bandeira")
    private Boolean temBandeira;
    @Column(name = "necessita_pilha")
    private Boolean necessitaPilha;

    public Mecanismo getMecanismo() {
        return mecanismo;
    }

    public void setMecanismo(Mecanismo mecanismo) {
        this.mecanismo = mecanismo;
    }

    public Boolean getTemBandeira() {
        return temBandeira;
    }

    public void setTemBandeira(Boolean temBandeira) {
        this.temBandeira = temBandeira;
    }

    public Boolean getNecessitaPilha() {
        return necessitaPilha;
    }

    public void setNecessitaPilha(Boolean necessitaPilha) {
        this.necessitaPilha = necessitaPilha;
    }
}
