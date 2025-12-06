package br.unitins.tp1.model.DTO.Usuario;

public record RedefinirSenhaRequestDTO(
        String username,
        String cpf,
        String novaSenha
) {
}
