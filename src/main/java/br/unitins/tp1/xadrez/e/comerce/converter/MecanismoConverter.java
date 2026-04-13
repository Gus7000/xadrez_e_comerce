package br.unitins.tp1.xadrez.e.comerce.converter;

import br.unitins.tp1.xadrez.e.comerce.model.Mecanismo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MecanismoConverter implements AttributeConverter<Mecanismo, Long> {
    @Override
    public Long convertToDatabaseColumn(Mecanismo mecanismo) {
        return mecanismo == null ? null : mecanismo.getId();
    }

    @Override
    public Mecanismo convertToEntityAttribute(Long id) {
        return id == null ? null : Mecanismo.valueOf(id);
    }
}
