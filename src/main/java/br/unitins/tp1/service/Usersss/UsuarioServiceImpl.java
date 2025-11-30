package br.unitins.tp1.service.Usersss;

import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoResponseDTO;
import br.unitins.tp1.model.DTO.Usuario.*;
import br.unitins.tp1.model.Endereco.Endereco;
import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Telefone;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.TelefoneRepository;
import br.unitins.tp1.repository.UsuarioRepository;
import br.unitins.tp1.service.Auth.HashServiceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
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

    @Inject
    TelefoneRepository telefoneRepository;

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

    @Override
    @Transactional
    public void redefinirSenhaUsuario(RedefinirSenhaRequestDTO dto) {
      Usuario usuario = usuarioRepository.findByUsernameAndCpf(dto.username(), dto.cpf());

        if (usuario == null) {
            throw new ValidationException("Autenticação", "Usuário não encontrado ou CPF incorreto.");
        }

        usuario.setSenha(hashService.getHashSenha(dto.novaSenha()));

    }

    @Override
    @Transactional
    public void updateDadosPessoais(Long idUsuario, DadosPessoaisRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(idUsuario);

        if (usuario == null) {
            throw new ValidationException("Usuario", "Usuário não encontrado");
        }

        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setEmail(dto.email());
        usuario.setDataNascimento(dto.dataNascimento());

        if (dto.telefoneRequestDTO() != null) {
            Telefone telefone;

            if (usuario.getTelefones().isEmpty()) {
                telefone = new Telefone();
                telefone.setUsuario(usuario);

                telefone.setDdd(dto.telefoneRequestDTO().ddd());
                telefone.setNumero(dto.telefoneRequestDTO().numero());

                telefoneRepository.persist(telefone);
                usuario.getTelefones().add(telefone);

            } else {
                telefone = usuario.getTelefones().get(0);
                telefone.setDdd(dto.telefoneRequestDTO().ddd());
                telefone.setNumero(dto.telefoneRequestDTO().numero());
            }
        }
    }

    @Transactional
    public void atualizarCredenciais(Long idUsuario, UpdateCredenciaisDTO dto) {
        // 1. Busca o usuário (Dono da conta)
        Usuario usuario = usuarioRepository.findById(idUsuario);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        // 2. Valida se o username antigo informado bate com o do banco
        if (!usuario.getUsername().equals(dto.usernameAntigo())) {
            throw new IllegalArgumentException("O username antigo não confere.");
        }

        // 3. Valida se a senha antiga bate (Comparar Hash)
        // Supondo que seu hashService tenha um método verify ou matches
        // Ex: hashService.getHashSenha(dto.senhaAntiga()).equals(usuario.getSenha())
        // Abaixo um exemplo genérico:
        if (!hashService.verificarSenha(dto.senhaAntiga(), usuario.getSenha())) {
            throw new IllegalArgumentException("A senha antiga está incorreta.");
        }

        // 4. Regra: Nova senha não pode ser igual à atual
        if (hashService.verificarSenha(dto.novaSenha(), usuario.getSenha())) {
            throw new IllegalArgumentException("A nova senha deve ser diferente da atual.");
        }

        // 5. Valida troca de Username (se houver mudança)
        if (!dto.usernameAntigo().equals(dto.novoUsername())) {
            // Verifica se o NOVO username já existe no banco (para outro id)
            if (usuarioRepository.findByUsername(dto.novoUsername()) != null) {
                throw new IllegalArgumentException("Este novo username já está em uso.");
            }
            usuario.setUsername(dto.novoUsername());
        }

        // 6. Atualiza a senha (Gera novo Hash)
        usuario.setSenha(hashService.getHashSenha(dto.novaSenha()));

        // O @Transactional cuida do persist/merge ao final do método
    }
}
