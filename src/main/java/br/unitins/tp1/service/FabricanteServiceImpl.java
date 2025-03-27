package br.unitins.tp1.service;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteRequestDTO;
import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Fabricante;
import br.unitins.tp1.repository.FabricanteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class FabricanteServiceImpl implements FabricanteService{
    @Inject
    FabricanteRepository servicerepository;

    @Override
    @Transactional
    public FabricanteResponseDTO create(FabricanteRequestDTO dto) {
        Fabricante fabricante = new Fabricante();

        fabricante.setNome(dto.nome());
        fabricante.setCnpj(dto.cnpj());
        fabricante.setPaisSede(dto.paisSede());
        fabricante.setTelefone(dto.telefone());

        servicerepository.persist(fabricante);

        return FabricanteResponseDTO.valueOf(fabricante);
    }

    @Override
    @Transactional
    public void update(long id, FabricanteRequestDTO dto) {
        Fabricante edicao = servicerepository.findById(id);

        edicao.setNome(dto.nome());
        edicao.setCnpj(dto.cnpj());
        edicao.setPaisSede(dto.paisSede());
        edicao.setTelefone(dto.telefone());

    }

    @Override
    @Transactional
    public void delete(long id) {
        servicerepository.deleteById(id);
    }

    @Override
    public FabricanteResponseDTO findById(long id) {
        return FabricanteResponseDTO.valueOf(servicerepository.findById(id));
    }

    @Override
    public List<FabricanteResponseDTO> findAll() {
        return servicerepository.findAll().stream().map(e -> FabricanteResponseDTO.valueOf(e)).toList();
    }
}
