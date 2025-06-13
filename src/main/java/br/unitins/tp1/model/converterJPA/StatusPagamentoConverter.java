package br.unitins.tp1.model.converterJPA;

import br.unitins.tp1.model.Pagamento.StatusPagamento;
import br.unitins.tp1.model.Pedido.StatusPedido;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusPagamentoConverter implements AttributeConverter<StatusPagamento, Integer> {

    @Override
    public Integer convertToDatabaseColumn(StatusPagamento statusPagamento) {
        return statusPagamento == null ? null : statusPagamento.getID();
    }

    @Override
    public StatusPagamento convertToEntityAttribute(Integer id) {
        return StatusPagamento.valueOf(id);
    }
    
}
