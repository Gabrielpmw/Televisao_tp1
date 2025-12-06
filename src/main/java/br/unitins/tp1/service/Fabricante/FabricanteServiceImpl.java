package br.unitins.tp1.service.Fabricante;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteRequestDTO;
import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.model.DTO.Marca.MarcaResponseDTO;
import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;

import br.unitins.tp1.model.PessoaJuridica.Fabricante;
import br.unitins.tp1.model.Telefone;
import br.unitins.tp1.repository.FabricanteRepository;
import br.unitins.tp1.repository.MarcaRepository;
import br.unitins.tp1.repository.TelefoneRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FabricanteServiceImpl implements FabricanteService {

    @Inject
    FabricanteRepository fabricanteRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    MarcaRepository marcaRepository;


    @Override
    @Transactional
    public FabricanteResponseDTO create(FabricanteRequestDTO dto) {
        Fabricante fabricante = new Fabricante();
        fabricante.setRazaoSocial(dto.razaoSocial());
        fabricante.setAnoFundacao(dto.dataAbertura());
        fabricante.setCnpj(dto.cnpj());
        fabricante.setPaisSede(dto.paisSede());
        fabricante.setAtivo(true); // Garante que nasce ativo

        fabricanteRepository.persist(fabricante);

        if (dto.telefones() != null) {
            for (TelefoneRequestDTO requestDTO : dto.telefones()) {
                Telefone telefone = new Telefone();
                telefone.setDdd(requestDTO.ddd());
                telefone.setNumero(requestDTO.numero());
                telefone.setFabricante(fabricante);
                fabricante.getTelefones().add(telefone);
                telefoneRepository.persist(telefone);
            }
        }
        return FabricanteResponseDTO.valueOf(fabricante);
    }

    @Override
    @Transactional
    public void update(long id, FabricanteRequestDTO dto) {
        Fabricante edicao = fabricanteRepository.findById(id);
        if (edicao == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }
        edicao.setRazaoSocial(dto.razaoSocial());
        edicao.setCnpj(dto.cnpj());
        edicao.setAnoFundacao(dto.dataAbertura());
        edicao.setPaisSede(dto.paisSede());

        // Limpa telefones antigos (estratégia simples de delete-insert)
        edicao.getTelefones().clear();
        telefoneRepository.flush();

        if (dto.telefones() != null) {
            for (TelefoneRequestDTO requestDTO : dto.telefones()) {
                Telefone telefone = new Telefone();
                telefone.setDdd(requestDTO.ddd());
                telefone.setNumero(requestDTO.numero());
                telefone.setFabricante(edicao);
                edicao.getTelefones().add(telefone);
            }
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }
        // Soft Delete: Apenas desativa
        fabricante.setAtivo(false);
    }

    @Transactional
    public void alterarStatus(long id, boolean ativo) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        if (fabricante == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }
        fabricante.setAtivo(ativo);
    }

    @Override
    public FabricanteResponseDTO findById(long id) {
        Fabricante fabricante = fabricanteRepository.findById(id);
        // Regra de negócio: findById padrão só retorna se estiver ativo
        if (fabricante == null || !fabricante.getAtivo()) {
            throw new NotFoundException("Fabricante não encontrado ou inativo");
        }
        return FabricanteResponseDTO.valueOf(fabricante);
    }

    @Override
    public List<FabricanteResponseDTO> findAll(int page, int pageSize) {
        PanacheQuery<Fabricante> query = fabricanteRepository.find("ativo", true)
                .page(Page.of(page, pageSize));

        return query.list().stream()
                .map(FabricanteResponseDTO::valueOf)
                .toList();
    }

    // --- Métodos de Listagem da Lixeira ---

    public List<FabricanteResponseDTO> findInativos(int page, int pageSize) {
        PanacheQuery<Fabricante> query = fabricanteRepository.find("ativo", false)
                .page(Page.of(page, pageSize));

        return query.list().stream()
                .map(FabricanteResponseDTO::valueOf)
                .toList();
    }

    public long countInativos() {
        return fabricanteRepository.count("ativo", false);
    }

    // --- Métodos Auxiliares e Buscas Específicas ---

    @Override
    public List<MarcaResponseDTO> findMarcasByFabricante(long idFabricante) {
        return fabricanteRepository.findMarcasByFabricante(idFabricante)
                .stream()
                .map(MarcaResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<FabricanteResponseDTO> findByNome(String nome, int page, int pageSize) {
        PanacheQuery<Fabricante> query = fabricanteRepository.findByNome(nome)
                .page(Page.of(page, pageSize));

        return query.list().stream()
                .map(FabricanteResponseDTO::valueOf)
                .toList();
    }

    @Override
    public long count() {
        return fabricanteRepository.count("ativo", true);
    }

    @Override
    public long count(String nome) {
        return fabricanteRepository.count("UPPER(razaoSocial) LIKE ?1 AND ativo = true", "%" + nome.toUpperCase() + "%");
    }

    public List<FabricanteResponseDTO> buscarTodos() {
        return fabricanteRepository.find("ativo", true).list().stream().map(FabricanteResponseDTO::valueOf).toList();
    }
}
