package br.unitins.tp1.service.Televisao;

import br.unitins.tp1.model.DTO.Modelo.ModeloResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Televisao.Televisao;

import java.util.List;

public interface TelevisaoService {
    TelevisaoResponseDTO create(TelevisaoRequestDTO tv);
    void update(long id, TelevisaoRequestDTO tv);
    void delete(long id);
    Televisao findById(long id);
    List<TelevisaoResponseDTO> findAll(int page, int pageSize);
    ModeloResponseDTO findTelevisaoByModelo(long idTelevisao);
    long count();
    long count(String nome);
    List<TelevisaoResponseDTO> findByModelo(String nomeModelo, int page, int pageSize);
}
