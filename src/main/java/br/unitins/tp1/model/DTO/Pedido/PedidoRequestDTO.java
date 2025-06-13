package br.unitins.tp1.model.DTO.Pedido;

import br.unitins.tp1.model.DTO.Pedido.ItemPedido.ItemPedidoRequestDTO;

import java.util.List;

public record PedidoRequestDTO(
        long idEndereco,
        List<ItemPedidoRequestDTO> itens
) {
}
