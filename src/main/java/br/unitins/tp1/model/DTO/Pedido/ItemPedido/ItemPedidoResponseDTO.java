package br.unitins.tp1.model.DTO.Pedido.ItemPedido;

import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Pedido.ItemPedido;

public record ItemPedidoResponseDTO(
        long id,
        TelevisaoResponseDTO televisao,
        int quantidadeTelevisao,
        double total
) {

    public static ItemPedidoResponseDTO valueOf(ItemPedido item) {
        return new ItemPedidoResponseDTO(
                item.getId(),
                TelevisaoResponseDTO.valueOf(item.getTelevisao()),
                item.getQuantidade(),
                item.getPreco()
        );
    }
}
