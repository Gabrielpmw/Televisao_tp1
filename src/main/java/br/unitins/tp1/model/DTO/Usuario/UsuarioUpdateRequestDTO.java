package br.unitins.tp1.model.DTO.Usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioUpdateRequestDTO(
        @NotBlank(message = "O username antigo não pode ser vazio")
        String usernameAntigo,

        @NotBlank(message = "A senha antiga não pode ser vazia")
        @Size(min = 6, message = "A senha antiga deve ter pelo menos 6 caracteres")
        String senhaAntiga,

        @NotBlank(message = "O novo username não pode ser vazio")
        @Size(max = 50, message = "O novo username deve ter no máximo 50 caracteres")
        String novoUsername,

        @NotBlank(message = "A nova senha não pode ser vazia")
        @Size(min = 6, message = "A nova senha deve ter pelo menos 6 caracteres")
        String novaSenha
) {
}
