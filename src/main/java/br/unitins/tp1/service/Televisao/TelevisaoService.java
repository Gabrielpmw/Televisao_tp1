package br.unitins.tp1.service.Televisao;

import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Televisao.Televisao;

import java.util.List;

public interface TelevisaoService {
    TelevisaoResponseDTO create(TelevisaoRequestDTO tv);
    void update(long id, TelevisaoRequestDTO tv);
    void delete(long id);
    Televisao findById(long id);
    List<TelevisaoResponseDTO> findAll();
    TelevisaoResponseDTO findTelevisaoByModelo(String modelo);
}
