package br.unitins.tp1.model.DTO.Pagamento;

import br.unitins.tp1.model.Pagamento.*;

import java.time.LocalDateTime;

public record PagamentoResponseDTO(
        long id,
        double valor,
        StatusPagamento statusPagamento,
        LocalDateTime dataPagamento,
        String formaPagamento
) {
    public static PagamentoResponseDTO valueOf(Pagamento pagamento){
        if (pagamento instanceof Pix){
            return new PagamentoResponseDTO(pagamento.getId(), pagamento.getValor(), pagamento.getStatusPagamento(),
                    pagamento.getDataPagamento(), "pix");
        }else if (pagamento instanceof Boleto){
            return new PagamentoResponseDTO(pagamento.getId(), pagamento.getValor(), pagamento.getStatusPagamento(),
                    pagamento.getDataPagamento(), "boleto");
        } else if (pagamento instanceof Cartao) {
            return new PagamentoResponseDTO(pagamento.getId(), pagamento.getValor(), pagamento.getStatusPagamento(),
                    pagamento.getDataPagamento(), "cartão");
        }else {
            return null;
        }
    }
}
