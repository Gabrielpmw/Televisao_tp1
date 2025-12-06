package br.unitins.tp1.model.DTO.Pedido;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.UniqueElements;

import java.util.List;

public record PedidoRequestDTO(

        @NotBlank(message = "Id de endereço deve ser informado")
        @Positive(message = "Informe apenas valores positivos")
        long idEndereco,

        @NotNull(message = "Informe os itens que deseja comprar")
        @UniqueElements(message = "Não repita dados")
        // O tipo da lista agora referencia o Record aninhado 'ItemPedidoRequestDTO'
        List<ItemPedidoRequestDTO> itens,

        Double valorTotal,
        Double valorFrete
) {
        // 💡 SOLUÇÃO: Inner Record (Classe aninhada) para o item do pedido
        // Não precisa de imports, pois está definida aqui.
        public record ItemPedidoRequestDTO(
                @NotNull(message = "O ID da Televisão é obrigatório")
                Long idTelevisao,

                @NotNull(message = "A quantidade é obrigatória")
                @Positive(message = "A quantidade deve ser positiva")
                Integer quatidade
        ) {}
}