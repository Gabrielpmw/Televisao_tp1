package br.unitins.tp1.service;

import br.unitins.tp1.model.DTO.Televisao.DimensaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.DimensaoResponseDTO;
import br.unitins.tp1.model.Televisao.Dimensao;
import br.unitins.tp1.repository.DimensaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class DimensaoServiceImpl implements DimensaoService{
    @Inject
    DimensaoRepository dimensaoRepository;

    @Override
    @Transactional
    public DimensaoResponseDTO create(DimensaoRequestDTO dto) {
        Dimensao dimensao = new Dimensao();

        dimensao.setComprimento(dto.comprimento());
        dimensao.setAltura(dto.altura());
        dimensao.setPolegada(dto.polegada());

        dimensaoRepository.persist(dimensao);

        return DimensaoResponseDTO.valueOf(dimensao);
    }

    @Override
    @Transactional
    public void update(long id, DimensaoRequestDTO dto) {
        Dimensao dimensao = dimensaoRepository.findById(id);

        dimensao.setComprimento(dto.comprimento());
        dimensao.setAltura(dto.altura());
        dimensao.setPolegada(dto.polegada());
    }

    @Override
    @Transactional
    public void delete(long id) {
        dimensaoRepository.deleteById(id);
    }

    @Override
    public DimensaoResponseDTO findById(long id) {
        return DimensaoResponseDTO.valueOf(dimensaoRepository.findById(id));
    }

    @Override
    public List<DimensaoResponseDTO> findAll() {
        return dimensaoRepository.findAll().stream().map(DimensaoResponseDTO::valueOf).toList();
    }
}
