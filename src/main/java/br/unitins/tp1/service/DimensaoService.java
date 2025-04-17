package br.unitins.tp1.service;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteRequestDTO;
import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.DimensaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.DimensaoResponseDTO;

import java.util.List;

public interface DimensaoService {
    DimensaoResponseDTO create(DimensaoRequestDTO dto);
    void update(long id, DimensaoRequestDTO dto);
    void delete(long id);
    DimensaoResponseDTO findById(long id);
    List<DimensaoResponseDTO> findAll();
}
