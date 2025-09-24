package br.unitins.tp1.service.Marca;

import br.unitins.tp1.model.DTO.Marca.MarcaRequestDTO;
import br.unitins.tp1.model.DTO.Marca.MarcaResponseDTO;

import java.util.List;

public interface MarcaService {
    MarcaResponseDTO create(MarcaRequestDTO dto);
    void update(long id, MarcaRequestDTO dto);
    void delete(long id);
    MarcaResponseDTO findById(long id);
    List<MarcaResponseDTO> findAll();
}
