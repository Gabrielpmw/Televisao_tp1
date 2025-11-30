package br.unitins.tp1.service.Funcionario;

import br.unitins.tp1.model.DTO.Funcionario.FuncionarioDeleteRequestDTO;
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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.Objects; // Importa Objects para o método findById

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

        // 1. DADOS BÁSICOS DO FUNCIONÁRIO
        funcionario.setNome(dto.nome());
        funcionario.setCpf(dto.cpf());

        // 2. DADOS DE USUÁRIO (LOGIN/PERFIL)
        usuario.setUsername(dto.username());
        usuario.setSenha(hashService.getHashSenha(dto.senha()));
        usuario.setPerfil(Perfil.ADM);

        // Novos campos de perfil (Sobrenome e Email)
        usuario.setSobrenome(dto.sobrenome());
        usuario.setEmail(dto.email());

        // Relacionamento
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

    // =========================================================================
    // 1. ATUALIZAÇÃO SEGURA DE DADOS (USADO NO FORMULÁRIO DE EDIÇÃO)
    // =========================================================================
    @Transactional
    public void updateDadosComValidacao(FuncionarioUpdateDadosDTO dto) {

        Funcionario funcionario = funcionarioRepository.findById(dto.idFuncionario());

        if (funcionario == null) {
            throw new ValidationException("Funcionario", "Funcionário alvo não encontrado.");
        }

        Usuario usuarioAlvo = funcionario.getUsuario();

        // Usuário logado
        String usernameLogado = jwt.getSubject();
        Usuario usuarioLogado = usuarioRepository.findByUsername(usernameLogado);

        // SENHA DIGITADA NO FRONT
        String senhaHasheadaDigitada = hashService.getHashSenha(dto.senhaAtualAlvo());

        // =====================================================================
        // CASO 1 — Usuário editando a SI MESMO
        // =====================================================================
        if (usuarioLogado.getId().equals(usuarioAlvo.getId())) {

            if (!usuarioLogado.getSenha().equals(senhaHasheadaDigitada)) {
                throw new ValidationException("Segurança", "Sua senha atual está incorreta.");
            }
        }

        // =====================================================================
        // CASO 2 — ADM editando OUTRO usuário
        // =====================================================================
        else {

            // Aqui valida a senha do ALVO, pois a regra exige isso
            if (!usuarioAlvo.getSenha().equals(senhaHasheadaDigitada)) {
                throw new ValidationException("Segurança", "A senha atual do funcionário está incorreta.");
            }
        }

        // =====================================================================
        // Se passou na validação, aplica as mudanças
        // =====================================================================
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

        // Usuário logado
        String usernameLogado = jwt.getSubject();
        Usuario usuarioLogado = usuarioRepository.findByUsername(usernameLogado);

        String senhaDigitadaHash = hashService.getHashSenha(senhaDigitada);

        // CASO 1 — Excluindo a si mesmo
        if (usuarioLogado.getId().equals(usuarioAlvo.getId())) {
            if (!usuarioLogado.getSenha().equals(senhaDigitadaHash)) {
                throw new ValidationException("Segurança", "Sua senha está incorreta.");
            }
        }
        // CASO 2 — ADM excluindo outro funcionário
        else {
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

    @Override
    public List<FuncionarioResponseDTO> findAll() {
        return funcionarioRepository.findAll().stream().map(FuncionarioResponseDTO::valueOf).toList();
    }

    @Override
    public FuncionarioResponseDTO findByUsername(String username) {
        Funcionario funcionario = funcionarioRepository.findByUsername(username);
        if (Objects.isNull(funcionario))
            throw new ValidationException("Funcionario", "Funcionário não encontrado pelo Username: " + username);

        return FuncionarioResponseDTO.valueOf(funcionario);
    }

    // =========================================================================
    // 2. ATUALIZAÇÃO DE CREDENCIAIS (Para o ADM usar no futuro)
    // =========================================================================

    /**
     * Permite que um usuário (ADM) altere o username e a senha de OUTRO USUÁRIO.
     * Necessita do ID do funcionário e do DTO de atualização de credenciais.
     */
    @Transactional
    public void updateCredenciaisFuncionario(long idFuncionario, UsuarioUpdateRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(idFuncionario);
        if (funcionario == null) {
            throw new ValidationException("Funcionario", "Funcionário não encontrado.");
        }

        Usuario usuario = funcionario.getUsuario();

        // NOTA: Para ADM, não precisamos da senha antiga do ALVO, apenas da nova.
        // O UsuarioUpdateRequestDTO exige senhaAntiga, mas ignoraremos a validação aqui
        // pois o ADM (com role 'adm') tem permissão para forçar a troca.

        if (dto.novoUsername() != null) {
            usuario.setUsername(dto.novoUsername());
        }

        if (dto.novaSenha() != null) {
            // Sempre hashear a nova senha
            usuario.setSenha(hashService.getHashSenha(dto.novaSenha()));
        }

        // Opcional: Persistir o Usuario se houver mudanças, mas o @Transactional já cuida disso.
    }

    /**
     * Permite a redefinição de senha de um funcionário a partir de um DTO simples
     * (Usado para 'Esqueci a Senha' ou troca simples pelo ADM).
     */
    @Transactional
    public void redefinirSenhaFuncionario(RedefinirSenhaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByUsernameAndCpf(dto.username(), dto.cpf());

        if (usuario == null) {
            throw new ValidationException("Autenticação", "Funcionário não encontrado ou CPF incorreto.");
        }

        // Verifica se o usuário encontrado é realmente um funcionário (Perfil.ADM)
        if (usuario.getPerfil() != Perfil.ADM || usuario.getFuncionario() == null) {
            throw new ValidationException("Usuário", "Perfil inválido para redefinição administrativa.");
        }

        // Persiste a nova senha hasheada
        usuario.setSenha(hashService.getHashSenha(dto.novaSenha()));
    }
}