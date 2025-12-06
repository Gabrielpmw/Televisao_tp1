package br.unitins.tp1.service.Usersss;

import br.unitins.tp1.model.DTO.Usuario.*;
import br.unitins.tp1.model.DTO.Usuario.Admin.UsuarioCreateAdminDTO;
import br.unitins.tp1.model.DTO.Usuario.Admin.UsuarioUpdateCredenciaisAdminDTO;
import br.unitins.tp1.model.DTO.Usuario.Admin.UsuarioUpdateDadosAdminDTO;
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

    // =========================================================================
    // LÓGICA DO CLIENTE / AUTO-SERVIÇO
    // =========================================================================

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
        // Método de deleção usado apenas pelo ADM, sem validação de senha (porque é por ID)
        Set<String> grupos = jwt.getGroups();

        if (!grupos.contains("adm")) {
            // Se não for admin, impede o acesso.
            throw new ValidationException("Permissão", "Você não tem permissão para usar este método.");
        }

        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        usuarioRepository.deleteById(id);
    }

    // -------------------------------------------------------------------------
    // NOVO MÉTODO: CLIENTE DELETA A PRÓPRIA CONTA (COM CONFIRMAÇÃO DE SENHA)
    // -------------------------------------------------------------------------
    @Transactional
    public void deleteContaCliente(ConfirmacaoSenhaDTO dto) {
        String username = jwt.getSubject();
        Usuario usuario = usuarioRepository.findByUsername(username);
        // Gera o hash da senha fornecida para comparação
        String senhaHashFornecida = hashService.getHashSenha(dto.senha());

        if (usuario == null) {
            throw new NotFoundException("Usuário autenticado não encontrado.");
        }

        // 1. Validação de Senha: Compara o hash da senha fornecida com a senha salva no banco
        if (!usuario.getSenha().equals(senhaHashFornecida)) {
            throw new ValidationException("Autenticação", "Senha de confirmação incorreta.");
        }

        // 2. Validação de Perfil (Segurança)
        if (!usuario.getPerfil().equals(Perfil.USER)) {
            throw new ValidationException("Permissão", "Apenas clientes podem usar este recurso de auto-deleção.");
        }

        // 3. Deleção
        // O mapeamento em cascata na entidade Usuario garante que Telefone, Endereço e Pedidos sejam deletados.
        usuarioRepository.deleteById(usuario.getId());
    }
    // -------------------------------------------------------------------------


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
        String cpfLimpo = dto.cpf().replaceAll("\\D", "");

        Usuario usuario = usuarioRepository.findByUsernameAndCpf(dto.username(), cpfLimpo);

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
        usuario.setCpf(dto.cpf());

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
        Usuario usuario = usuarioRepository.findById(idUsuario);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        if (!usuario.getUsername().equals(dto.usernameAntigo())) {
            throw new IllegalArgumentException("O username antigo não confere.");
        }

        if (!hashService.getHashSenha(dto.senhaAntiga()).equals(usuario.getSenha())) {
            throw new IllegalArgumentException("A senha antiga está incorreta.");
        }

        if (hashService.getHashSenha(dto.novaSenha()).equals(usuario.getSenha())) {
            throw new IllegalArgumentException("A nova senha deve ser diferente da atual.");
        }

        if (!dto.usernameAntigo().equals(dto.novoUsername())) {
            if (usuarioRepository.findByUsername(dto.novoUsername()) != null) {
                throw new IllegalArgumentException("Este novo username já está em uso.");
            }
            usuario.setUsername(dto.novoUsername());
        }

        usuario.setSenha(hashService.getHashSenha(dto.novaSenha()));
    }

    // =========================================================================
    // LÓGICA DO ADMINISTRADOR (Novos Métodos)
    // =========================================================================

    @Transactional
    public UsuarioResponseDTO createByAdmin(UsuarioCreateAdminDTO dto) {
        // Validação básica de unicidade
        if (usuarioRepository.findByUsername(dto.username()) != null) {
            throw new ValidationException("Username", "O username '" + dto.username() + "' já está em uso.");
        }

        // Aqui assumimos que não existe um método findByEmail, caso exista, valide também.

        Usuario usuario = new Usuario();

        // Dados de Acesso
        usuario.setUsername(dto.username());
        usuario.setSenha(hashService.getHashSenha(dto.senha()));

        // Dados Pessoais
        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());
        usuario.setDataNascimento(dto.dataNascimento());

        // Perfil Fixo (Regra de Negócio: Admin cria Clientes)
        usuario.setPerfil(Perfil.USER);

        // Telefone (Opcional)
        if (dto.telefone() != null) {
            Telefone telefone = new Telefone();
            telefone.setDdd(dto.telefone().ddd());
            telefone.setNumero(dto.telefone().numero());
            telefone.setUsuario(usuario);
            usuario.setTelefones(List.of(telefone));
        }

        usuarioRepository.persist(usuario);
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Transactional
    public UsuarioResponseDTO updateDadosByAdmin(Long id, UsuarioUpdateDadosAdminDTO dto) {
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        // Atualiza apenas dados cadastrais (Sem tocar em senha/username)
        usuario.setNome(dto.nome());
        usuario.setSobrenome(dto.sobrenome());
        usuario.setCpf(dto.cpf());
        usuario.setEmail(dto.email());
        usuario.setDataNascimento(dto.dataNascimento());

        // Lógica de Telefone (Igual ao updateDadosPessoais)
        if (dto.telefone() != null) {
            Telefone telefone;
            if (usuario.getTelefones() == null || usuario.getTelefones().isEmpty()) {
                telefone = new Telefone();
                telefone.setUsuario(usuario);
                telefone.setDdd(dto.telefone().ddd());
                telefone.setNumero(dto.telefone().numero());
                telefoneRepository.persist(telefone);
                // Se a lista for nula, inicializa, se for vazia, adiciona
                // (Assumindo que getTelefones retorna lista do Hibernate)
                usuario.getTelefones().add(telefone);
            } else {
                telefone = usuario.getTelefones().get(0);
                telefone.setDdd(dto.telefone().ddd());
                telefone.setNumero(dto.telefone().numero());
            }
        }

        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Transactional
    public void updateCredenciaisByAdmin(Long id, UsuarioUpdateCredenciaisAdminDTO dto) {
        Usuario usuario = usuarioRepository.findById(id);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado.");
        }

        // 1. Verifica troca de Username
        if (!usuario.getUsername().equals(dto.username())) {
            // Se mudou o nome, verifica se o novo já existe
            if (usuarioRepository.findByUsername(dto.username()) != null) {
                throw new ValidationException("Username", "O username informado já está em uso por outro usuário.");
            }
            usuario.setUsername(dto.username());
        }

        // 2. Atualiza a senha (Admin define nova senha diretamente)
        usuario.setSenha(hashService.getHashSenha(dto.novaSenha()));
    }
}