package br.unitins.tp1.model.DTO.Televisao;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TelevisaoRequestDTO(
        @NotBlank(message = "A marca não pode estar em branco")
        String marca,

        @NotBlank(message = "O modelo não pode estar em branco")
        String modelo,

        @NotNull(message = "O valor não pode estar em branco")
        @Positive(message = "Informe apenas valore positivos")
        Double valor,

        @NotNull(message = "O tipoe de resolução é obrigatório")
        Integer idTipoResolucao,

        @NotNull(message = "O tipo de tela é obrigatório")
        @Positive(message = "Informe apenas valores positivos")
        Integer idTipoTela,

        @NotNull(message = "O estoque é obrigatório")
        @Positive(message = "Informe apenas valores positivos")
        Integer estoque,

        @NotNull(message = "A dimensão é obrigatória")
        @Positive(message = "Informe apenas valores positivos")
        long idDimensao,

        @NotNull(message = "O fabricante é obrigatório")
        @Positive(message = "Informe apenas valores positivos")
        long idFabricante) {

}
