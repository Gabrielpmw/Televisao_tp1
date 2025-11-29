package br.unitins.tp1.model.DTO.Pedido.ItemPedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ItemPedidoRequestDTO(

        @NotBlank(message = "Id de televisão deve ser informado")
        @Positive(message = "Informe apenas valores positivos")
        int idTelevisao,

        @NotBlank(message = "A quantidade deve ser informado")
        @Positive(message = "Informe apenas valores positivos")
        int quatidade
) {
}
