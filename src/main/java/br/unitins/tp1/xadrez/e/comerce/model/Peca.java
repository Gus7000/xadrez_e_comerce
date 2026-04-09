package br.unitins.tp1.xadrez.e.comerce.model;

import br.unitins.tp1.xadrez.e.comerce.converter.CorPecaConverter;
import br.unitins.tp1.xadrez.e.comerce.converter.TipoPecaConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;

@Entity
public class Peca extends ComponenteFisico{
    @Convert(converter = CorPecaConverter.class)
    private CorPeca cor;
    
    @Convert(converter = TipoPecaConverter.class)
    private TipoPeca tipo;
    
    public CorPeca getCor() {
        return cor;
    }
    public void setCor(CorPeca cor) {
        this.cor = cor;
    }
    public TipoPeca getTipo() {
        return tipo;
    }
    public void setTipo(TipoPeca tipo) {
        this.tipo = tipo;
    }
}
