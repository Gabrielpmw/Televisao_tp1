package br.unitins.tp1.service.Telefone;

import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
import br.unitins.tp1.model.DTO.Telefone.TelefoneResponseDTO;


import java.util.List;

public interface TelefoneService {
    TelefoneResponseDTO create(TelefoneRequestDTO dto);

    void update(long id, TelefoneRequestDTO dto);

    void delete(long id);

    TelefoneResponseDTO findById(long id);

    List<TelefoneResponseDTO> findAll();

    List<TelefoneResponseDTO> findTelefonesByDDD(String ddd);
}
