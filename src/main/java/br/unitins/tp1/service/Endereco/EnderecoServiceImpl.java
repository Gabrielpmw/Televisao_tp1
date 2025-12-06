package br.unitins.tp1.service.Endereco;

import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoRequestDTO;
import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoResponseDTO;
import br.unitins.tp1.model.DTO.Endereco.Estado.EstadoResponseDTO;
import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioResponseDTO;
import br.unitins.tp1.model.DTO.Usuario.UsuarioResponseDTO;
import br.unitins.tp1.model.Endereco.Endereco;
import br.unitins.tp1.model.Endereco.Municipio;
import br.unitins.tp1.model.Pagamento.Pagamento;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.EnderecoRepository;
import br.unitins.tp1.repository.EstadoRepository;
import br.unitins.tp1.repository.MunicipioRepository;
import br.unitins.tp1.repository.UsuarioRepository;
import br.unitins.tp1.resource.AuthResource;
import br.unitins.tp1.service.Usersss.UsuarioServiceImpl;
import br.unitins.tp1.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    UsuarioServiceImpl usuarioService;

    @Inject
    JsonWebToken jwt;

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @Override
    @Transactional
    public EnderecoResponseDTO create(EnderecoRequestDTO dto) {
        // ... (validações iniciais iguais ao seu código) ...
        String username = jwt.getSubject();

        Municipio municipio = municipioRepository.findById(dto.idMunicipio());
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) throw new ValidationException("Usuário", "Usuário não encontrado");

        Endereco endereco = new Endereco();
        endereco.setCep(dto.cep());
        endereco.setBairro(dto.bairro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setMunicipio(municipio);

        // VINCULO IMPORTANTE: Diz ao endereço quem é o dono
        endereco.setUsuario(usuario);

        // CORREÇÃO CRÍTICA AQUI:
        // Não faça: List<Endereco> enderecos = new ArrayList<>();
        // Faça isso:
        if (usuario.getEnderecos() == null) {
            usuario.setEnderecos(new ArrayList<>());
        }
        usuario.getEnderecos().add(endereco); // Adiciona na lista existente

        enderecoRepository.persist(endereco);

        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    @Transactional
    public void update(long id, EnderecoRequestDTO dto) {
        String username = jwt.getSubject();
        Set<String> grupos = jwt.getGroups();

        if (dto == null) {
            throw new ValidationException("dto", "O dto não pode estar vazio");
        }
        if (dto.idMunicipio() == null || dto.idMunicipio() <= 0) {
            throw new ValidationException("dto", "id município inválido");
        }

        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw new ValidationException("Endereço", "Endereço não encontrado");
        }

        Usuario usuario = usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new ValidationException("Usuário", "Usuário não encontrado");
        }

        Municipio municipio = municipioRepository.findById(dto.idMunicipio());
        if (municipio == null) {
            throw new ValidationException("Município", "Município não encontrado");
        }

        for (String grupo : grupos) {
            if (grupo.equalsIgnoreCase("adm")) {
                endereco.setCep(dto.cep());
                endereco.setBairro(dto.bairro());
                endereco.setNumero(dto.numero());
                endereco.setComplemento(dto.complemento());
                endereco.setMunicipio(municipio);
                return;
            }
        }

        if (!endereco.getUsuario().getUsername().equalsIgnoreCase(username)) {
            throw new ValidationException("Endereço", "Este endereço não pertence ao usuário autenticado");
        }
        endereco.setCep(dto.cep());
        endereco.setBairro(dto.bairro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setMunicipio(municipio);
    }

    @Override
    @Transactional
    public void delete(long idEndereco) {
        String username = jwt.getSubject();

        // Busca o endereço
        Endereco endereco = enderecoRepository.findById(idEndereco);

        // CORREÇÃO: Verificação de segurança (Evita o NullPointerException)
        if (endereco == null) {
            // Se já foi deletado ou não existe, podemos só retornar ou lançar erro amigável
            throw new ValidationException("Endereço", "Endereço não encontrado ou já deletado.");
        }

        Usuario usuario = usuarioRepository.findByUsername(username);

        // Lógica de Adm
        Set<String> grupos = jwt.getGroups();
        for (String grupo : grupos) {
            if (grupo.equalsIgnoreCase("adm")) {
                enderecoRepository.deleteById(idEndereco);
                return;
            }
        }

        // Verifica se o endereço pertence ao usuário
        if (endereco.getUsuario() != null && username.equalsIgnoreCase(endereco.getUsuario().getUsername())) {
            enderecoRepository.deleteById(idEndereco);
        } else {
            throw new ValidationException("Endereço", "Este endereço não te pertence");
        }
    }

    @Override
    public EnderecoResponseDTO findById(long id) {
        if (id <= 0) {
            throw new ValidationException("Id", "Digite um id válido");
        }

        Endereco endereco = enderecoRepository.findById(id);
        if (endereco == null) {
            throw new ValidationException("Endereço", "Endereço não encontrado");
        }
        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    public List<EnderecoResponseDTO> findAll() {
        return enderecoRepository.findAll().stream().map(EnderecoResponseDTO::valueOf).toList();
    }

    public List<EnderecoResponseDTO> findByCEP(String cep) {
        if (cep == null) {
            throw new ValidationException("CEP", "CEP não pode ficar vazio");
        }

        List<Endereco> enderecos = enderecoRepository.findByCep(cep);

        if (enderecos == null || enderecos.isEmpty()) {
            throw new ValidationException("Endereço", "Nenhum endereço encontrado com esse CEP");
        }

        return enderecoRepository.findByCep(cep).stream().map(EnderecoResponseDTO::valueOf).toList();
    }

    @Override
    public List<EnderecoResponseDTO> findMyEndereco() {
        String username = jwt.getSubject();
        Usuario usuario = usuarioRepository.findByUsername(username);

        if (usuario == null) {
            throw new ValidationException("Usuário", "Usuário não encontrado");
        }

        List<Endereco> enderecosDoUsuario = enderecoRepository.findByUsuarioId(usuario.getId());

        // CORREÇÃO CRÍTICA AQUI:
        // Se a lista for vazia, retorna lista vazia (Status 200 OK)
        // O seu código antigo lançava "throw new ValidationException", gerando o erro 400.
        if (enderecosDoUsuario == null || enderecosDoUsuario.isEmpty()) {
            return new ArrayList<>();
        }

        return enderecosDoUsuario.stream()
                .map(EnderecoResponseDTO::valueOf)
                .toList();
    }
}
