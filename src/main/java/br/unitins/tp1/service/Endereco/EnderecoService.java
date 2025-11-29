package br.unitins.tp1.service.Endereco;

import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoRequestDTO;
import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoResponseDTO;
import br.unitins.tp1.model.DTO.Endereco.Estado.EstadoResponseDTO;


import java.util.List;

public interface EnderecoService {
    EnderecoResponseDTO create(EnderecoRequestDTO dto);
    void update(long id, EnderecoRequestDTO dto);
    void delete(long idEndereco);
    EnderecoResponseDTO findById(long id);
    List<EnderecoResponseDTO> findAll();
    List<EnderecoResponseDTO> findMyEndereco();
}
