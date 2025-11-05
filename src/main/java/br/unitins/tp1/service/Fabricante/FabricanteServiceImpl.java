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
        fabricante.setStatus(dto.status());
        fabricante.setPaisSede(dto.paisSede());

        fabricanteRepository.persist(fabricante);

        for (TelefoneRequestDTO requestDTO : dto.telefones()) {
            Telefone telefone = new Telefone();
            telefone.setDdd(requestDTO.ddd());
            telefone.setNumero(requestDTO.numero());
            telefone.setFabricante(fabricante);
            fabricante.getTelefones().add(telefone);

            telefoneRepository.persist(telefone);
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

        // ... (atualiza razaoSocial, dataAbertura, cnpj, etc.) ...

        // 1. Limpa a lista (Isso agenda os DELETES)
        edicao.getTelefones().clear();

        // 2. FORÇA A EXECUÇÃO dos deletes AGORA
        // (Você precisa ter o 'telefoneRepository' injetado no seu service)
        telefoneRepository.flush(); // <-- ADICIONE ESTA LINHA

        // 3. Agora, adicione os novos telefones (agendando os INSERTS)
        // Como os deletes já rodaram, não haverá conflito de chave.
        if (dto.telefones() != null) { // <-- É bom adicionar esta verificação
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
        fabricanteRepository.deleteById(id);
    }

    @Override
    public FabricanteResponseDTO findById(long id) {
        return FabricanteResponseDTO.valueOf(fabricanteRepository.findById(id));
    }

    @Override
    public List<FabricanteResponseDTO> findAll(int page, int pageSize) {
        PanacheQuery<Fabricante> query = fabricanteRepository.findAll().page(Page.of(page, pageSize));

        return query.list().stream()
                .map(FabricanteResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<MarcaResponseDTO> findMarcasByFabricante(long idFabricante) {
        return fabricanteRepository.findMarcasByFabricante(idFabricante).stream().map(MarcaResponseDTO::valueOf).toList();
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
        return fabricanteRepository.findAll().count();
    }

    @Override
    public long count(String nome) {
        return 0;
    }
}
