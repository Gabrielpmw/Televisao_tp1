package br.unitins.tp1.service;

import br.unitins.tp1.model.DTO.TelevisaoRequestDTO;
import br.unitins.tp1.model.DTO.TelevisaoResponseDTO;
import br.unitins.tp1.model.Televisao;

import java.util.List;

public interface TelevisaoService {
    TelevisaoResponseDTO create(TelevisaoRequestDTO tv);
    void update(long id, TelevisaoRequestDTO tv);
    void delete(long id);
    Televisao findById(long id);
    List<TelevisaoResponseDTO> findAll();
    TelevisaoResponseDTO findByMarca(String marca);
    List<TelevisaoResponseDTO> findByModelo(String modelo);
}
