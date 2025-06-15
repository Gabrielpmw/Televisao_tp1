package br.unitins.tp1.model.DTO.Pagamento;

import br.unitins.tp1.model.Pagamento.Cartao;
import br.unitins.tp1.model.Pagamento.StatusPagamento;

import java.time.LocalDateTime;

public record CartaoResponseDTO(
        long id,
        double valor,
        String ultimosDigitos,
        StatusPagamento statusPagamento,
        LocalDateTime dataPagamento,
        String titular
) {

    public static CartaoResponseDTO valueOf(Cartao cartao){

        String ultimosDigitos = cartao.getNumero().substring(cartao.getNumero().length() - 4);

        return new CartaoResponseDTO(cartao.getId(),
                cartao.getValor(), ultimosDigitos,
                cartao.getStatusPagamento(),
                cartao.getDataPagamento(),
                cartao.getTitular());
    }
}
