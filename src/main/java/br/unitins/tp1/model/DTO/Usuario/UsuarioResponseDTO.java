package br.unitins.tp1.model.DTO.Usuario;

import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Usuario;

public record UsuarioResponseDTO(
        Long id,
        String username,
        String cpf,
        Perfil perfil
) {
    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario == null)
            return null;
        return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername(), usuario.getCpf(), usuario.getPerfil());
    }
}
