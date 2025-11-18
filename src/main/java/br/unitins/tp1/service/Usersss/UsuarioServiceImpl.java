package br.unitins.tp1.service.Usersss;

import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoResponseDTO;
import br.unitins.tp1.model.DTO.Usuario.UsuarioCreateRequestDTO;
import br.unitins.tp1.model.DTO.Usuario.UsuarioResponseDTO;
import br.unitins.tp1.model.DTO.Usuario.UsuarioUpdateRequestDTO;
import br.unitins.tp1.model.Endereco.Endereco;
import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.UsuarioRepository;
import br.unitins.tp1.service.Auth.HashServiceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;
import br.unitins.tp1.validation.ValidationException;

import java.util.List;
import java.util.Set;

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

        if (dto.username() == null || dto.senha() == null || dto.cpf() == null){
            throw new ValidationException("Usuario", "Usuário inválido, tente de novo");
        }

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
        String username = jwt.getSubject();
        Set<String> grupos = jwt.getGroups();

        if (usuario == null){
            throw new ValidationException("Usuario", "Usuário não existe");
        }
        for (String grupo : grupos) {
            if (grupo.equalsIgnoreCase("adm")) {
                usuarioRepository.deleteById(usuario.getId());
                return;
            } else if (!usuario.getUsername().equalsIgnoreCase(username)) {
                throw new ValidationException("Usuário", "Este usuário não é seu");
            }
        }
        String senhaAntiga = hashService.getHashSenha(dto.senhaAntiga());

        if (!usuario.getSenha().equals(senhaAntiga)) {
            throw new ValidationException("Usuario", "Username ou senha errado");
        }

        if (!usuario.getUsername().equals(dto.usernameAntigo())) {
            throw new ValidationException("Usuario", "Username ou senha errado");
        }

        usuario.setUsername(dto.novoUsername());
        usuario.setSenha(hashService.getHashSenha(dto.novaSenha()));
    }

    @Override
    @Transactional
    public void delete(long id) {
        String username = jwt.getSubject();
        Usuario usuario = usuarioRepository.findById(id);
        Set<String> grupos = jwt.getGroups();

        for (String grupo : grupos) {
            if (grupo.equalsIgnoreCase("adm")) {
                usuarioRepository.deleteById(id);
                return;
            } else if (!usuario.getUsername().equalsIgnoreCase(username)) {
                throw new ValidationException("Usuário", "Este usuário não é seu");
            }
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    public UsuarioResponseDTO findByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null){
            throw new ValidationException("Usuário", "Usuário não existe");
        }

        return UsuarioResponseDTO.valueOf(usuarioRepository.findByUsername(username));
    }

    @Override
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.listAll().stream().map(UsuarioResponseDTO::valueOf).toList();
    }

    @Override
    public UsuarioResponseDTO findById(long id) {
        if (id <= 0){
            throw new ValidationException("Id", "Id inválido");
        }

        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null){
            throw new ValidationException("Usuario", "Usuário não existe");
        }

        return UsuarioResponseDTO.valueOf(usuarioRepository.findById(id));
    }
}
