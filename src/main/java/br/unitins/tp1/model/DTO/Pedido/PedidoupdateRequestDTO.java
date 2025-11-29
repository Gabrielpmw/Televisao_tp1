package br.unitins.tp1.model.DTO.Pedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PedidoupdateRequestDTO(

        @NotBlank(message = "Id de endereço deve ser informado")
        @Positive(message = "Informe apenas valores positivos")
        long idEndereco,

        @NotBlank(message = "Id de televisão deve ser informado")
        String status
) {
}
