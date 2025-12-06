package br.unitins.tp1.service.Funcionario;

import br.unitins.tp1.model.DTO.Funcionario.FuncionarioRequestDTO;
import br.unitins.tp1.model.DTO.Funcionario.FuncionarioResponseDTO;
import br.unitins.tp1.model.DTO.Funcionario.FuncionarioUpdateDadosDTO;
import br.unitins.tp1.model.DTO.Usuario.RedefinirSenhaRequestDTO;
import br.unitins.tp1.model.DTO.Usuario.UsuarioUpdateRequestDTO;
import br.unitins.tp1.model.Funcionario;
import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.FuncionarioRepository;
import br.unitins.tp1.repository.UsuarioRepository;
import br.unitins.tp1.service.Auth.HashServiceImpl;
import br.unitins.tp1.validation.ValidationException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class FuncionarioServiceImpl implements FuncionarioService {

    @Inject
    FuncionarioRepository funcionarioRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    HashServiceImpl hashService;

    @Inject
    JsonWebToken jwt;

    @Override
    @Transactional
    public FuncionarioResponseDTO create(FuncionarioRequestDTO dto) {
        Funcionario funcionario = new Funcionario();
        Usuario usuario = new Usuario();

        funcionario.setNome(dto.nome());
        funcionario.setCpf(dto.cpf());

        usuario.setUsername(dto.username());
        usuario.setSenha(hashService.getHashSenha(dto.senha()));
        usuario.setPerfil(Perfil.ADM);

        usuario.setSobrenome(dto.sobrenome());
        usuario.setEmail(dto.email());

        usuario.setFuncionario(funcionario);
        funcionario.setUsuario(usuario);

        usuarioRepository.persist(usuario);
        funcionarioRepository.persist(funcionario);

        return FuncionarioResponseDTO.valueOf(funcionario);
    }

    @Override
    @Transactional
    public void update(long id, FuncionarioRequestDTO dto) {
        throw new UnsupportedOperationException("O método update (FuncionarioRequestDTO) não é mais suportado. Use updateDadosComValidacao.");
    }

    @Transactional
    public void updateDadosComValidacao(FuncionarioUpdateDadosDTO dto) {

        Funcionario funcionario = funcionarioRepository.findById(dto.idFuncionario());

        if (funcionario == null) {
            throw new ValidationException("Funcionario", "Funcionário alvo não encontrado.");
        }

        Usuario usuarioAlvo = funcionario.getUsuario();
        String usernameLogado = jwt.getSubject();
        Usuario usuarioLogado = usuarioRepository.findByUsername(usernameLogado);
        String senhaHasheadaDigitada = hashService.getHashSenha(dto.senhaAtualAlvo());

        if (usuarioLogado.getId().equals(usuarioAlvo.getId())) {
            if (!usuarioLogado.getSenha().equals(senhaHasheadaDigitada)) {
                throw new ValidationException("Segurança", "Sua senha atual está incorreta.");
            }
        } else {
            if (!usuarioAlvo.getSenha().equals(senhaHasheadaDigitada)) {
                throw new ValidationException("Segurança", "A senha atual do funcionário está incorreta.");
            }
        }

        funcionario.setNome(dto.nome());
        funcionario.setCpf(dto.cpf());
        usuarioAlvo.setSobrenome(dto.sobrenome());
        usuarioAlvo.setEmail(dto.email());
    }

    @Transactional
    public void delete(Long idFuncionario, String senhaDigitada) {

        if (senhaDigitada == null || senhaDigitada.isBlank()) {
            throw new ValidationException("Segurança", "Senha não informada.");
        }

        Funcionario funcionario = funcionarioRepository.findById(idFuncionario);
        if (funcionario == null) {
            throw new ValidationException("Funcionario", "Funcionário alvo não encontrado.");
        }

        Usuario usuarioAlvo = funcionario.getUsuario();
        Usuario usuarioLogado = usuarioRepository.findByUsername(jwt.getSubject());
        String senhaDigitadaHash = hashService.getHashSenha(senhaDigitada);

        if (usuarioLogado.getId().equals(usuarioAlvo.getId())) {
            if (!usuarioLogado.getSenha().equals(senhaDigitadaHash)) {
                throw new ValidationException("Segurança", "Sua senha está incorreta.");
            }
        } else {
            if (!usuarioAlvo.getSenha().equals(senhaDigitadaHash)) {
                throw new ValidationException("Segurança", "A senha do funcionário está incorreta.");
            }
        }

        funcionarioRepository.delete(funcionario);
    }

    @Override
    public FuncionarioResponseDTO findById(long id) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        if (Objects.isNull(funcionario))
            throw new ValidationException("Funcionario", "Funcionário não encontrado pelo ID: " + id);

        return FuncionarioResponseDTO.valueOf(funcionario);
    }

    // =========================================================================
    //  MÉTODOS DE BUSCA PAGINADA E CONTAGEM
    // =========================================================================

    public List<FuncionarioResponseDTO> findAll(int page, int pageSize) {
        PanacheQuery<Funcionario> query = funcionarioRepository.findAll().page(Page.of(page, pageSize));
        return query.list().stream().map(FuncionarioResponseDTO::valueOf).toList();
    }

    @Override
    public List<FuncionarioResponseDTO> findAll() {
        return funcionarioRepository.findAll().stream().map(FuncionarioResponseDTO::valueOf).toList();
    }

    // Busca Paginada por NOME
    public List<FuncionarioResponseDTO> findByNome(String nome, int page, int pageSize) {
        PanacheQuery<Funcionario> query = funcionarioRepository.findByNome(nome)
                .page(Page.of(page, pageSize));

        return query.list().stream()
                .map(FuncionarioResponseDTO::valueOf)
                .toList();
    }

    // --- AQUI ESTAVA FALTANDO ESTE MÉTODO ---
    // Busca Paginada por USERNAME (Para o admin listar usuários parecidos)
    public List<FuncionarioResponseDTO> findByUsername(String username, int page, int pageSize) {
        // Atenção: Certifique-se que seu Repository tem o método findByUsernameLike ou similar retornando PanacheQuery
        // Se não tiver, use o find normal com query string aqui mesmo:
        PanacheQuery<Funcionario> query = funcionarioRepository.find("UPPER(usuario.username) LIKE ?1", username.toUpperCase() + "%")
                .page(Page.of(page, pageSize));

        return query.list().stream()
                .map(FuncionarioResponseDTO::valueOf)
                .toList();
    }

    // Contagem Total
    public long count() {
        return funcionarioRepository.count();
    }

    // Contagem Filtrada (Nome)
    public long count(String nome) {
        if (nome == null) return 0;
        return funcionarioRepository.findByNome(nome).count();
    }

    // --- E ESTE TAMBÉM ESTAVA FALTANDO ---
    // Contagem Filtrada (Username)
    public long countByUsername(String username) {
        if (username == null) return 0;
        return funcionarioRepository.find("UPPER(usuario.username) LIKE ?1", username.toUpperCase() + "%").count();
    }

    // =========================================================================

    // Busca Exata (Login/Validação)
    public FuncionarioResponseDTO findByUsername(String username) {
        Funcionario funcionario = funcionarioRepository.findByUsername(username);
        if (Objects.isNull(funcionario))
            throw new ValidationException("Funcionario", "Funcionário não encontrado pelo Username: " + username);

        return FuncionarioResponseDTO.valueOf(funcionario);
    }

    // =========================================================================

    @Transactional
    public void updateCredenciaisFuncionario(long idFuncionario, UsuarioUpdateRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario);
        if (funcionario == null) {
            throw new ValidationException("Funcionario", "Funcionário não encontrado.");
        }

        Usuario usuario = funcionario.getUsuario();

        if (dto.novoUsername() != null) {
            usuario.setUsername(dto.novoUsername());
        }

        if (dto.novaSenha() != null) {
            usuario.setSenha(hashService.getHashSenha(dto.novaSenha()));
        }
    }

    @Transactional
    public void redefinirSenhaFuncionario(RedefinirSenhaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByUsernameAndCpf(dto.username(), dto.cpf());

        if (usuario == null) {
            throw new ValidationException("Autenticação", "Funcionário não encontrado ou CPF incorreto.");
        }

        if (usuario.getPerfil() != Perfil.ADM || usuario.getFuncionario() == null) {
            throw new ValidationException("Usuário", "Perfil inválido para redefinição administrativa.");
        }

        usuario.setSenha(hashService.getHashSenha(dto.novaSenha()));
    }
}