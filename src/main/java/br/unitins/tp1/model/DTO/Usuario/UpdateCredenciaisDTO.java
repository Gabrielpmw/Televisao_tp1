package br.unitins.tp1.model.DTO.Usuario;

import jakarta.validation.constraints.NotBlank;

public record UpdateCredenciaisDTO(
        @NotBlank(message = "O username atual é obrigatório")
        String usernameAntigo,

        @NotBlank(message = "A senha atual é obrigatória")
        String senhaAntiga,

        @NotBlank(message = "O novo username é obrigatório")
        String novoUsername,

        @NotBlank(message = "A nova senha é obrigatória")
        String novaSenha
) {
}
