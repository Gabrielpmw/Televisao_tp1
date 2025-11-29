package br.unitins.tp1.service.Funcionario;

import br.unitins.tp1.model.DTO.Funcionario.FuncionarioRequestDTO;
import br.unitins.tp1.model.DTO.Funcionario.FuncionarioResponseDTO;
import br.unitins.tp1.model.Funcionario;
import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.FuncionarioRepository;
import br.unitins.tp1.repository.UsuarioRepository;
import br.unitins.tp1.service.Auth.HashServiceImpl;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class FuncionarioServiceImpl implements FuncionarioService{

    @Inject
    FuncionarioRepository funcionarioRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    HashServiceImpl hashService;

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

        usuario.setFuncionario(funcionario);
        funcionario.setUsuario(usuario);

        usuarioRepository.persist(usuario);

        funcionarioRepository.persist(funcionario);

        return FuncionarioResponseDTO.valueOf(funcionario);
    }

    @Override
    @Transactional
    public void update(long id, FuncionarioRequestDTO dto) {
        Funcionario funcionario = funcionarioRepository.findById(id);
        Usuario usuario = usuarioRepository.findById(funcionario.getUsuario().getId());

        funcionario.setNome(dto.nome());
        funcionario.setCpf(dto.cpf());

        usuario.setUsername(dto.username());
        usuario.setSenha(dto.senha());
    }

    @Override
    @Transactional
    public void delete(long id) {
        funcionarioRepository.deleteById(id);
    }

    @Override
    public FuncionarioResponseDTO findById(long id) {
        return FuncionarioResponseDTO.valueOf(funcionarioRepository.findById(id));
    }

    @Override
    public List<FuncionarioResponseDTO> findAll() {
        return funcionarioRepository.findAll().stream().map(FuncionarioResponseDTO::valueOf).toList();
    }

    @Override
    public FuncionarioResponseDTO findByUsername(String username) {
        return FuncionarioResponseDTO.valueOf(funcionarioRepository.findByUsername(username));
    }
}
