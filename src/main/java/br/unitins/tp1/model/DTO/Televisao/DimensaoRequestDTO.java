package br.unitins.tp1.model.DTO.Televisao;

import jakarta.validation.constraints.NotBlank;

public record DimensaoRequestDTO(
        @NotBlank(message = "Comprimento deve ser informado")
        int comprimento,

        @NotBlank(message = "Altura deve ser informado")
        int altura,

        @NotBlank(message = "Polegada deve ser informado")
        int polegada
) {
}
