package br.unitins.tp1.service;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteRequestDTO;
import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Fabricante;
import br.unitins.tp1.model.Telefone;
import br.unitins.tp1.repository.FabricanteRepository;
import br.unitins.tp1.repository.TelefoneRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class FabricanteServiceImpl implements FabricanteService {
    @Inject
    FabricanteRepository fabricanteRepository;
    @Inject
    TelefoneRepository telefoneRepository;

    @Override
    @Transactional
    public FabricanteResponseDTO create(FabricanteRequestDTO dto) {
        Fabricante fabricante = new Fabricante();

        fabricante.setNome(dto.nome());
        fabricante.setCnpj(dto.cnpj());
        fabricante.setPaisSede(dto.paisSede());

        List<Telefone> telefones = dto.idTelefone().stream()
                .map(id -> telefoneRepository.findById(id))
                .filter(Objects::nonNull).toList();

        fabricante.setTelefones(telefones);

        fabricanteRepository.persist(fabricante);

        return FabricanteResponseDTO.valueOf(fabricante);
    }

    @Override
    @Transactional
    public void update(long id, FabricanteRequestDTO dto) {
        Fabricante edicao = fabricanteRepository.findById(id);

        edicao.setNome(dto.nome());
        edicao.setCnpj(dto.cnpj());
        edicao.setPaisSede(dto.paisSede());

        List<Telefone> novosTelefones = dto.idTelefone().stream()
                .map(telefoneRepository::findById)
                .filter(Objects::nonNull)
                .toList();

        edicao.getTelefones().clear();
        for (Telefone telefone : novosTelefones) {
            telefone.setFabricante(edicao);
            edicao.getTelefones().add(telefone);
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

    @Transactional
    @Override
    public List<FabricanteResponseDTO> findAll() {
        return fabricanteRepository.findAll().stream().map(FabricanteResponseDTO::valueOf).toList();
    }
}
