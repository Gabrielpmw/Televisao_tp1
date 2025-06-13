package br.unitins.tp1.service.Fornecedor;

import br.unitins.tp1.model.DTO.Fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.model.DTO.Fornecedor.FornecedorResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;


import java.util.List;

public interface FornecedorService {
    FornecedorResponseDTO create(FornecedorRequestDTO dto);
    void update(long id, FornecedorRequestDTO dto);
    void delete(long id);
    FornecedorResponseDTO findById(long id);
    List<FornecedorResponseDTO> findAll();
    List<TelevisaoResponseDTO> findTelevisaoByFornecedor(long idFornecedor);
    FornecedorResponseDTO findFornecedorByTelefone(long idTelefone);
}
