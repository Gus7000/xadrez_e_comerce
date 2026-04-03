package br.unitins.tp1.xadrez.e.comerce.converter;

import br.unitins.tp1.xadrez.e.comerce.model.CorPeca;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CorPecaConverter implements AttributeConverter<CorPeca, Long>{

    @Override
    public Long convertToDatabaseColumn(CorPeca corPeca) {
        return corPeca == null ? null : corPeca.getId();
    }

    @Override
    public CorPeca convertToEntityAttribute(Long id) {
        return CorPeca.valueOf(id);
    }
    
}
