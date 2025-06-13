package br.unitins.tp1.model.DTO;

public record AuthRequestDTO(
        String username,
        String senha,
        String perfil
) {
}
