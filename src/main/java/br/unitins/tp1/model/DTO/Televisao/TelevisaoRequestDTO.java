package br.unitins.tp1.model.DTO.Televisao;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TelevisaoRequestDTO(
        @NotBlank(message = "A marca não pode estar em branco")
        String marca,

        @NotBlank(message = "O modelo não pode estar em branco")
        String modelo,

        @NotBlank(message = "A resolução não pode estar em branco")
        String resolucao,

        @NotNull(message = "O tipo de tela é obrigatório")
        Integer idTipoTela,

        @NotNull(message = "A dimensão é obrigatória")
        long idDimensao,

        @NotNull(message = "O fabricante é obrigatório")
        long idFabricante) {

}
