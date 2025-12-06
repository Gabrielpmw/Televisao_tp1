package br.unitins.tp1.model.DTO.Pagamento;

import br.unitins.tp1.model.Pagamento.Boleto;
import br.unitins.tp1.model.Pagamento.StatusPagamento;

import java.time.LocalDateTime;

public record BoletoResponseDTO(
        long id,
        double valor,
        String numeroBoleto,
        StatusPagamento statusPagamento,
        LocalDateTime dataPagamento
) {
    public static BoletoResponseDTO valueOf(Boleto boleto){
        return new BoletoResponseDTO(boleto.getId(),
                boleto.getValor(),
                boleto.getCodigoBarras(),
                boleto.getStatusPagamento(),
                boleto.getDataPagamento());
    }
}
