package br.unitins.tp1.model.DTO.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCreateRequestDTO(
        @NotBlank(message = "O username não pode ser vazio")
        @Size(max = 50, message = "O username deve ter no máximo 50 caracteres")
        String username,

        @NotBlank(message = "A senha não pode ser vazia")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
        String senha,

        @NotBlank(message = "O cpf não pode ser vazio")
        String cpf
        ) {
}
