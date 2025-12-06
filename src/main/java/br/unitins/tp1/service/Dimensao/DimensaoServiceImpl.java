package br.unitins.tp1.service.Dimensao;

import br.unitins.tp1.model.DTO.Televisao.DimensaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.DimensaoResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Televisao.Dimensao;
import br.unitins.tp1.repository.DimensaoRepository;
import br.unitins.tp1.validation.ValidationException;
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
        if (dto == null){
            throw new ValidationException("DTO", "O dto não pode estar vazio");
        }

        if (dto.altura() <= 0 || dto.polegada() <= 0 || dto.comprimento() <= 0){
            throw new ValidationException("DTO", "Escreva apenas valores positivos");
        }

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
        if (dto == null){
            throw new ValidationException("DTO", "O dto não pode estar vazio");
        }

        if (dto.altura() <= 0 || dto.polegada() <= 0 || dto.comprimento() <= 0){
            throw new ValidationException("DTO", "Escreva apenas valores positivos");
        }

        if (dto.altura() == null || dto.polegada() == null || dto.comprimento() == null){
            throw new ValidationException("DTO", "Não pode conter nenhum valor vazio");
        }

        Dimensao dimensao = dimensaoRepository.findById(id);

        dimensao.setComprimento(dto.comprimento());
        dimensao.setAltura(dto.altura());
        dimensao.setPolegada(dto.polegada());
    }

    @Override
    @Transactional
    public void delete(long id) {
        if (id <= 0){
            throw new ValidationException("id", "Escreva apenas valores positivos");
        }

        dimensaoRepository.deleteById(id);
    }

    @Override
    public DimensaoResponseDTO findById(long id) {
        if (id <= 0){
            throw new ValidationException("id", "Escreva apenas valores positivos");
        }

        return DimensaoResponseDTO.valueOf(dimensaoRepository.findById(id));
    }

    @Override
    public List<DimensaoResponseDTO> findAll() {
        return dimensaoRepository.findAll().stream().map(DimensaoResponseDTO::valueOf).toList();
    }

    @Override
    public List<TelevisaoResponseDTO> findTelevisaoByDimensao(long idDimensao) {
        if (idDimensao <= 0){
            throw new ValidationException("id", "Escreva apenas valores positivos");
        }

        return dimensaoRepository.findTelevisaoByDimensao(idDimensao).stream().map(TelevisaoResponseDTO::valueOf).toList();
    }
}
