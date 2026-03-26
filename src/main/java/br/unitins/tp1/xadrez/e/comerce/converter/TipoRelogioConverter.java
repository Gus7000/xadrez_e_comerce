package br.unitins.tp1.xadrez.e.comerce.converter;

import br.unitins.tp1.xadrez.e.comerce.model.TipoRelogio;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoRelogioConverter implements AttributeConverter<TipoRelogio, Long>{

    @Override
    public Long convertToDatabaseColumn(TipoRelogio tipoRelogio) {
        return tipoRelogio == null ? null : tipoRelogio.getId();
    }

    @Override
    public TipoRelogio convertToEntityAttribute(Long id) {
        return TipoRelogio.valueOf(id);
    }
    
}
