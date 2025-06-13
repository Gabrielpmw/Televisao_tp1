package br.unitins.tp1.service.Usersss;

import br.unitins.tp1.model.DTO.Usuario.UsuarioCreateRequestDTO;
import br.unitins.tp1.model.DTO.Usuario.UsuarioResponseDTO;
import br.unitins.tp1.model.DTO.Usuario.UsuarioUpdateRequestDTO;
import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.UsuarioRepository;
import br.unitins.tp1.service.Auth.HashServiceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {
    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    HashServiceImpl hashService;

    @Inject
    JsonWebToken jwt;

    @Override
    @Transactional
    public UsuarioResponseDTO create(UsuarioCreateRequestDTO dto) {
        Usuario usuario = new Usuario();

        usuario.setUsername(dto.username());
        usuario.setSenha(hashService.getHashSenha(dto.senha()));
        usuario.setCpf(dto.cpf());
        usuario.setPerfil(Perfil.USER);

        usuarioRepository.persist(usuario);

        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public void update(UsuarioUpdateRequestDTO dto) {

        Usuario usuario = usuarioRepository.findByUsername(dto.usernameAntigo());

        String senhaAntiga = hashService.getHashSenha(dto.senhaAntiga());
        if (!usuario.getSenha().equals(senhaAntiga)) {
            throw new ValidationException("Senha antiga errada");
        }

        if (!usuario.getUsername().equals(dto.usernameAntigo())) {
            throw new ValidationException("O username antigo está incorreto");
        }

        usuario.setUsername(dto.novoUsername());
        usuario.setSenha(hashService.getHashSenha(dto.novaSenha()));
    }

    @Override
    @Transactional
    public void delete(long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioResponseDTO findByUsername(String username) {
        return UsuarioResponseDTO.valueOf(usuarioRepository.findByUsername(username));
    }

    @Override
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.listAll().stream().map(UsuarioResponseDTO::valueOf).toList();
    }

    @Override
    public UsuarioResponseDTO findById(long id) {
        return UsuarioResponseDTO.valueOf(usuarioRepository.findById(id));
    }
}
