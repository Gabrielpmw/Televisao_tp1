package br.unitins.tp1.model.DTO.Endereco.Estado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EstadoRequestDTO(
        @NotBlank(message = "O campo Estado é obrigatório")
        @Size(max = 100, message = "O campo Estado deve ter no máximo 100 caracteres.")
        String estado,

        @NotBlank(message = "O campo sigla é obrigatório")
        @Size(max = 2, message = "O campo Estado deve ter no máximo 2 caracteres.")
        String sigla,

        Integer idRegiao
) {
}
