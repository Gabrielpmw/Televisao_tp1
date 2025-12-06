package br.unitins.tp1.model.DTO.Usuario.Admin;

import jakarta.validation.constraints.NotBlank;

public record UsuarioUpdateCredenciaisAdminDTO(
        @NotBlank(message = "O username é obrigatório")
        String username, // O admin pode manter o mesmo ou corrigir

        @NotBlank(message = "A nova senha é obrigatória")
        String novaSenha
) {
}
