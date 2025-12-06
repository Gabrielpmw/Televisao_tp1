package br.unitins.tp1.service.Dimensao;

import br.unitins.tp1.model.DTO.Televisao.DimensaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.DimensaoResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;

import java.util.List;

public interface DimensaoService {
    DimensaoResponseDTO create(DimensaoRequestDTO dto);
    void update(long id, DimensaoRequestDTO dto);
    void delete(long id);
    DimensaoResponseDTO findById(long id);
    List<DimensaoResponseDTO> findAll();
    List<TelevisaoResponseDTO> findTelevisaoByDimensao(long idDimensao);
}
