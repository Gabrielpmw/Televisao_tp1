package br.unitins.tp1.model.converterJPA;

import br.unitins.tp1.model.Endereco.Regiao;
import br.unitins.tp1.model.Perfil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)

public class RegiaoConverter implements AttributeConverter<Regiao, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Regiao regiao) {
        return regiao == null ? null : regiao.getID();

    }

    @Override
    public Regiao convertToEntityAttribute(Integer id) {
        return Regiao.valueOf(id);
    }
}
