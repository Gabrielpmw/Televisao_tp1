package br.unitins.tp1.model.DTO.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateRequestDTO(
        @NotBlank(message = "O username antigo não pode ser vazio")
        String usernameAntigo,

        @NotBlank(message = "A senha antiga não pode ser vazia")
        String senhaAntiga,

        @NotBlank(message = "O novo username não pode ser vazio")
        String novoUsername,

        @NotBlank(message = "A nova senha não pode ser vazia")
        String novaSenha
) {
}
