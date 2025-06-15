package br.unitins.tp1.model.DTO.Televisao;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DimensaoRequestDTO(

        @NotBlank(message = "Comprimento deve ser informado")
        @Positive(message = "Informe apenas valores positivos")
        Integer comprimento,

        @NotBlank(message = "Altura deve ser informado")
        @Positive(message = "Informe apenas valores positivos")
        Integer altura,

        @NotBlank(message = "Polegada deve ser informado")
        @Positive(message = "Informe apenas valores positivos")
        Integer polegada
) {
}
