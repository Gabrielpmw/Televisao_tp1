package br.unitins.tp1.model.DTO.Pedido;

import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoEntregaResponseDTO;
import br.unitins.tp1.model.DTO.Pedido.ItemPedido.ItemPedidoResponseDTO;
import br.unitins.tp1.model.Pedido.Pedido;
import br.unitins.tp1.model.Pedido.StatusPedido;

import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        long id,
        LocalDateTime dataPedido,
        double valorTotal,
        StatusPedido statusPedido,
        EnderecoEntregaResponseDTO enderecoEntrega,
        List<ItemPedidoResponseDTO> itens,
        String comprador
) {

    public static PedidoResponseDTO valueOf(Pedido pedido){
        if (pedido == null) return null;

        return new PedidoResponseDTO(pedido.getId(),
                pedido.getDataPedido(),
                pedido.getValorTotal(),
                pedido.getStatusPedido(),
                EnderecoEntregaResponseDTO.valueOf(pedido.getEnderecoEntrega()),
                pedido.getItensPedidos().stream().map(i -> ItemPedidoResponseDTO.valueOf(i)).toList(),
                pedido.getUsuario().getUsername());
        }
}
