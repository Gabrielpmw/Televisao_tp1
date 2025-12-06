package br.unitins.tp1.model.DTO.Televisao;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TelevisaoRequestDTO(
        @NotNull(message = "O valor não pode estar em branco")
        @Positive(message = "Informe apenas valore positivos")
        Double valor,

        @NotNull(message = "O tipoe de resolução é obrigatório")
        Integer idTipoResolucao,

        @NotNull(message = "O tipo de tela é obrigatório")
        @Positive(message = "Informe apenas valores positivos")
        Integer idTipoTela,

        @NotNull(message = "A descrição da televisão é obrigatório")
        String descricao,

        @NotNull(message = "O estoque é obrigatório")
        @Positive(message = "Informe apenas valores positivos")
        Integer estoque,

        @NotNull(message = "O fabricante é obrigatório")
        @Positive(message = "Informe apenas valores positivos")
        int altura,

        @NotNull(message = "O fabricante é obrigatório")
        @Positive(message = "Informe apenas valores positivos")
        int largura,

        @NotNull(message = "O fabricante é obrigatório")
        @Positive(message = "Informe apenas valores positivos")
        int polegada,

        @NotNull(message = "O idModelo é obrigatório")
        @Positive(message = "Informe apenas valores positivos")
        long idModelo
) {

}
