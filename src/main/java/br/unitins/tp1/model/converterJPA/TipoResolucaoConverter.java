package br.unitins.tp1.model.converterJPA;


import br.unitins.tp1.model.Televisao.TipoResolucao;
import br.unitins.tp1.model.Televisao.TipoTela;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TipoResolucaoConverter implements AttributeConverter<TipoResolucao, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TipoResolucao resolucao) {
        return resolucao == null? null: resolucao.getID();
    }

    @Override
    public TipoResolucao convertToEntityAttribute(Integer id) {
        return TipoResolucao.valueOf(id);
    }
}
