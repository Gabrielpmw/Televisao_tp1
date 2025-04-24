package br.unitins.tp1.service.Televisao;

import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Fabricante;
import br.unitins.tp1.model.Fornecedor;
import br.unitins.tp1.model.Televisao.Televisao;

import java.util.List;

public interface TelevisaoService {
    TelevisaoResponseDTO create(TelevisaoRequestDTO tv);
    void update(long id, TelevisaoRequestDTO tv);
    void delete(long id);
    Televisao findById(long id);
    List<TelevisaoResponseDTO> findAll();
    List<TelevisaoResponseDTO> findTelevisaoByTipoTela(int idTipoTela);
    TelevisaoResponseDTO findTelevisaoByModelo(String modelo);
}
