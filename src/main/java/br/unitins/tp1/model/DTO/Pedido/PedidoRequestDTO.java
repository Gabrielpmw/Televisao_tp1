package br.unitins.tp1.model.DTO.Pedido;

import br.unitins.tp1.model.DTO.Pedido.ItemPedido.ItemPedidoRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

public record PedidoRequestDTO(

        @NotBlank(message = "Id de endereço deve ser informado")
        @Positive(message = "Informe apenas valores positivos")
        long idEndereco,

        @NotBlank(message = "Informe os itens que deseja comprar")
        @UniqueElements(message = "Não repita dados")
        List<ItemPedidoRequestDTO> itens
) {
}
