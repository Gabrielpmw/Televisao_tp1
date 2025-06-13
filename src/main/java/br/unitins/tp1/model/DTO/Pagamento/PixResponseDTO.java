package br.unitins.tp1.model.DTO.Pagamento;

import br.unitins.tp1.model.Pagamento.Pix;
import br.unitins.tp1.model.Pagamento.StatusPagamento;

import java.time.LocalDateTime;

public record PixResponseDTO(
        long id,
        double valor,
        String chaveDestinatario,
        StatusPagamento statusPagamento,
        LocalDateTime dataPagamento
) {
    public static PixResponseDTO valueOf(Pix pix){
        return new PixResponseDTO(pix.getId(), pix.getValor(), pix.getChave(), pix.getStatusPagamento(), pix.getDataPagamento());
    }
}
