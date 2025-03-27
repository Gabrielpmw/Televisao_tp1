package br.unitins.tp1.service;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteRequestDTO;
import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Televisao;

import java.util.List;

public interface FabricanteService {
    FabricanteResponseDTO create(FabricanteRequestDTO dto);
    void update(long id, FabricanteRequestDTO dto);
    void delete(long id);
    FabricanteResponseDTO findById(long id);
    List<FabricanteResponseDTO> findAll();
}
