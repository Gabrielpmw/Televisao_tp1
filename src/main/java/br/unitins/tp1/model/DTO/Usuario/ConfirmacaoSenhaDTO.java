package br.unitins.tp1.model.DTO.Usuario;

import jakarta.validation.constraints.NotBlank;

public record ConfirmacaoSenhaDTO(
        @NotBlank(message = "A senha é obrigatória para confirmar a exclusão da conta")
        String senha
) {
}
