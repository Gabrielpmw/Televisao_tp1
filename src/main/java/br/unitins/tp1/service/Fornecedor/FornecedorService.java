package br.unitins.tp1.service.Fornecedor;

import br.unitins.tp1.model.DTO.Fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.model.DTO.Fornecedor.FornecedorResponseDTO;


import java.util.List;

public interface FornecedorService {
    FornecedorResponseDTO create(FornecedorRequestDTO dto);
    void update(long id, FornecedorRequestDTO dto);
    void delete(long id);
    FornecedorResponseDTO findById(long id);
    List<FornecedorResponseDTO> findAll();
}
