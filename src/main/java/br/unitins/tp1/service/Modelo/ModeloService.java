package br.unitins.tp1.service.Modelo;

import br.unitins.tp1.model.DTO.Marca.MarcaRequestDTO;
import br.unitins.tp1.model.DTO.Modelo.ModeloRequestDTO;
import br.unitins.tp1.model.DTO.Modelo.ModeloResponseDTO;

import java.util.List;

public interface ModeloService {
    ModeloResponseDTO create(ModeloRequestDTO dto);
    void update(long id, ModeloRequestDTO dto);
    void delete(long id);
    ModeloResponseDTO findById(long id);
    List<ModeloResponseDTO> findAll();
}
