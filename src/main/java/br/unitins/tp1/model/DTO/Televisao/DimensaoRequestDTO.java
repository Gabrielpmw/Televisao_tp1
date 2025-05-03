package br.unitins.tp1.model.DTO.Televisao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DimensaoRequestDTO(
        @NotNull(message = "O campo comprimento é obrigatório.")
        Integer comprimento,

        @NotNull(message = "O campo altura é obrigatório.")
        Integer altura,

        @NotNull(message = "O campo polegada é obrigatório.")
        Integer polegada
) {
}
