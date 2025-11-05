package br.unitins.tp1.service.Municipio;

import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoResponseDTO;
import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioRequestDTO;
import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioResponseDTO;


import java.util.List;

public interface MunicipioService {
    MunicipioResponseDTO create(MunicipioRequestDTO dto);
    void update(long id, MunicipioRequestDTO dto);
    void delete(long id);
    MunicipioResponseDTO findById(long id);
    List<MunicipioResponseDTO> findAll();
    List<EnderecoResponseDTO> findEnderecoByMunicipio(long id);
    long count();
    long count(String nome);
}
