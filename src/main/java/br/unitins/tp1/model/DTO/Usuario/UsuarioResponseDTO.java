package br.unitins.tp1.model.DTO.Usuario;

import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.model.DTO.Telefone.TelefoneResponseDTO;
import java.time.LocalDate;

public record UsuarioResponseDTO(
        Long id,
        String username,
        String cpf,
        Perfil perfil,
        String nome,
        String sobrenome,
        String email,
        TelefoneResponseDTO telefone,
        LocalDate dataNascimento
) {
    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario == null) return null;

        TelefoneResponseDTO telefoneDTO = null;
        if (usuario.getTelefones() != null && !usuario.getTelefones().isEmpty()) {
            telefoneDTO = TelefoneResponseDTO.valueOf(usuario.getTelefones().get(0));
        }

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.getCpf(),
                usuario.getPerfil(),
                usuario.getNome(),
                usuario.getSobrenome(),
                usuario.getEmail(),
                telefoneDTO,
                usuario.getDataNascimento()
        );
    }
}