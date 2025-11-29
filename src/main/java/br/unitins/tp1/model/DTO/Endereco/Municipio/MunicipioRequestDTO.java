package br.unitins.tp1.model.DTO.Endereco.Municipio;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MunicipioRequestDTO(
        @NotBlank(message = "O campo município não pode ser null.")
        @Size(max = 60, message = "O campo município deve ter no máximo 60 caracteres.")
        String municipio,

        @NotNull(message = "O campo id estado não pode ser null.")
        @Positive(message = "Você deve informar um valor positivo")
        Long idEstado
) {
}
