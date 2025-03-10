package br.unitins.tp1.model.converterJPA;

import br.unitins.tp1.model.TipoTela;
import jakarta.persistence.AttributeConverter;

public class TipoTelaConverter implements AttributeConverter<TipoTela, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoTela tela) {
        return tela == null? null: tela.getID();
    }
x
    @Override
    public TipoTela convertToEntityAttribute(Integer id) {
        return TipoTela.valueOf(id);
    }
}
