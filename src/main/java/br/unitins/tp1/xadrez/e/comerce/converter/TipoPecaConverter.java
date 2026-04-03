package br.unitins.tp1.xadrez.e.comerce.converter;

import br.unitins.tp1.xadrez.e.comerce.model.TipoPeca;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoPecaConverter implements AttributeConverter<TipoPeca, Long>{

    @Override
    public Long convertToDatabaseColumn(TipoPeca tipoPeca) {
        return tipoPeca == null ? null : tipoPeca.getId();
    }

    @Override
    public TipoPeca convertToEntityAttribute(Long id) {
        return TipoPeca.valueOf(id);
    }
    
}
