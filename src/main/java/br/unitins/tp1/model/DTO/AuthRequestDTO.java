package br.unitins.tp1.model.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AuthRequestDTO(

        @NotBlank(message = "Você deve informar o username")
        String username,

        @NotBlank(message = "Você deve informar a senha")
        String senha,

        @NotBlank(message = "Você deve informar o perfile")
        @Positive(message = "Deve informar apenas valores positivos")
        @Min(value = 1, message = "Informe o perfil correto")
        @Max(value = 2, message = "Informe o perfil correto")
        String perfil
) {
}
