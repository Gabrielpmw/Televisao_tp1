package br.unitins.tp1.model.converterJPA;


import br.unitins.tp1.model.Televisao.TipoTela;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoTelaConverter implements AttributeConverter<TipoTela, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoTela tela) {
        return tela == null? null: tela.getID();
    }

    @Override
    public TipoTela convertToEntityAttribute(Integer id) {
        return TipoTela.valueOf(id);
    }
}
