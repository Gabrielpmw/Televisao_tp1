package br.unitins.tp1.model.DTO.Telefone;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TelefoneRequestDTO(
        @NotBlank(message = "DDD deve ser informado")
        @Size(max = 2, message = "Máximo 2 caracteres")
        String ddd,

        @NotBlank(message = "Número deve ser informado")
        @Size(max = 9, message = "Máximo de 9 caracteres")
        String numero
) {
}
