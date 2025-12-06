package br.unitins.tp1.service.Marca;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.model.DTO.Fornecedor.FornecedorResponseDTO;
import br.unitins.tp1.model.DTO.Marca.MarcaRequestDTO;
import br.unitins.tp1.model.DTO.Marca.MarcaResponseDTO;
import br.unitins.tp1.model.DTO.Modelo.ModeloResponseDTO;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.PessoaJuridica.Fabricante;
import br.unitins.tp1.model.PessoaJuridica.Fornecedor;
import br.unitins.tp1.repository.FabricanteRepository;
import br.unitins.tp1.repository.MarcaRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class MarcaServiceImpl implements MarcaService{

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    @Override
    @Transactional
    public MarcaResponseDTO create(MarcaRequestDTO dto) {
        Marca marca = new Marca();
        Fabricante fabricante = fabricanteRepository.findById(dto.idFabricante());

        marca.setNomeMarca(dto.nomeMarca());
        marca.setDescricao(dto.descricao());
        marca.setFabricante(fabricante);

        // Define como ativo na criação
        marca.setAtivo(true);

        marcaRepository.persist(marca);

        return MarcaResponseDTO.valueOf(marca);
    }

    @Override
    @Transactional
    public void update(long id, MarcaRequestDTO dto) {
        Marca marca = marcaRepository.findById(id);
        if (marca == null) throw new NotFoundException("Marca não encontrada.");

        Fabricante fabricante = fabricanteRepository.findById(dto.idFabricante());

        marca.setNomeMarca(dto.nomeMarca());
        marca.setDescricao(dto.descricao());
        marca.setFabricante(fabricante);
    }

    @Override
    @Transactional
    public void delete(long id) {
        Marca marca = marcaRepository.findById(id);
        if (marca == null) throw new NotFoundException("Marca não encontrada.");

        // Soft Delete: Apenas desativa
        marca.setAtivo(false);
    }

    // --- NOVO MÉTODO: Alterar Status (Ativar/Desativar) ---
    @Transactional
    public void alterarStatus(long id, boolean ativo) {
        Marca marca = marcaRepository.findById(id);
        if (marca == null) throw new NotFoundException("Marca não encontrada.");

        marca.setAtivo(ativo);
    }

    @Override
    public MarcaResponseDTO findById(long id) {
        Marca marca = marcaRepository.findById(id);
        // Retorna apenas se estiver ativo (opcional, dependendo da regra de negócio)
        if (marca == null || !marca.getAtivo())
            throw new NotFoundException("Marca não encontrada ou inativa.");

        return MarcaResponseDTO.valueOf(marca);
    }

    @Override
    public List<MarcaResponseDTO> findAll(int page, int pageSize) {
        // Busca apenas ATIVOS
        PanacheQuery<Marca> query = marcaRepository.find("ativo", true).page(Page.of(page, pageSize));
        return query.list().stream().map(MarcaResponseDTO::valueOf).toList();
    }

    @Override
    public List<ModeloResponseDTO> findModeloByMarca(long idMarca) {
        // O método findModelosByMarca já filtra ativos no repositório
        return marcaRepository.findModelosByMarca(idMarca).stream().map(ModeloResponseDTO::valueOf).toList();
    }

    @Override
    public long count() {
        // Conta apenas ATIVOS
        return marcaRepository.count("ativo", true);
    }

    @Override
    public long count(String nome) {
        // Conta por nome E ativo
        return marcaRepository.count("UPPER(nomeMarca) LIKE ?1 AND ativo = true", "%" + nome.toUpperCase() + "%");
    }

    @Override
    public List<MarcaResponseDTO> findMarcaByModelo(String nome, int page, int pageSize) {
        // O método findByNome do repositório já foi ajustado para filtrar ativos
        PanacheQuery<Marca> query = marcaRepository.findByNome(nome)
                .page(Page.of(page, pageSize));

        return query.list().stream()
                .map(MarcaResponseDTO::valueOf)
                .toList();
    }

    // --- MÉTODOS PARA A LIXEIRA ---

    public List<MarcaResponseDTO> findInativos(int page, int pageSize) {
        PanacheQuery<Marca> query = marcaRepository.findInativos().page(Page.of(page, pageSize));
        return query.list().stream().map(MarcaResponseDTO::valueOf).toList();
    }

    public long countInativos() {
        return marcaRepository.count("ativo", false);
    }
}
