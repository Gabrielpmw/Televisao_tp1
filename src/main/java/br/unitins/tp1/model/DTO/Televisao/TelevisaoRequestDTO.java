package br.unitins.tp1.model.DTO.Televisao;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TelevisaoRequestDTO(
        @NotBlank(message = "A marca não pode estar em branco")
        String marca,

        @NotBlank(message = "O modelo não pode estar em branco")
        String modelo,

        @NotNull(message = "O tipoe de resolução é obrigatório")
        Integer idTipoResolucao,

        @NotNull(message = "O tipo de tela é obrigatório")
        Integer idTipoTela,

        @NotNull(message = "A dimensão é obrigatória")
        long idDimensao,

        @NotNull(message = "O fabricante é obrigatório")
        long idFabricante) {

}
