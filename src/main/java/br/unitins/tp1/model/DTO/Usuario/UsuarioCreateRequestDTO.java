package br.unitins.tp1.model.DTO.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCreateRequestDTO(
        @NotBlank(message = "O username não pode ser vazio")
        String username,

        @NotBlank(message = "A senha não pode ser vazia")
        String senha,

        @NotBlank(message = "O cpf não pode ser vazio")
        String cpf
        ) {
}
