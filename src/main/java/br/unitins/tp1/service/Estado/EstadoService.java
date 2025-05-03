package br.unitins.tp1.service.Estado;

import br.unitins.tp1.model.DTO.Endereco.Estado.EstadoRequestDTO;
import br.unitins.tp1.model.DTO.Endereco.Estado.EstadoResponseDTO;
import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioResponseDTO;

import java.util.List;

public interface EstadoService {
    EstadoResponseDTO create(EstadoRequestDTO dto);
    void update(long id, EstadoRequestDTO dto);
    void delete(long id);
    EstadoResponseDTO findById(long id);
    List<EstadoResponseDTO> findAll();
    EstadoResponseDTO findByNome(String nome);
    List<MunicipioResponseDTO> findMunicipioByEstado(long idEstado);
}
