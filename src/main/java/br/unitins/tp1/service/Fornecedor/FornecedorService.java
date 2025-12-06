package br.unitins.tp1.service.Fornecedor;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.model.DTO.Fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.model.DTO.Fornecedor.FornecedorResponseDTO;
import br.unitins.tp1.model.DTO.Marca.MarcaResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;


import java.util.List;

public interface FornecedorService {
    FornecedorResponseDTO create(FornecedorRequestDTO dto);
    void update(long id, FornecedorRequestDTO dto);
    void delete(long id);
    FornecedorResponseDTO findById(long id);
    List<FornecedorResponseDTO> findAll(int page, int pageSize);
    List<MarcaResponseDTO> marcaForFornecedor(long idFornecedor, List<Long> idMarcas);
    List<MarcaResponseDTO> findMarcaByFornecedor(long idFornecedor);
    long count();
    long count(String nome);
    List<FornecedorResponseDTO> findByNome(String nome, int page, int pageSize);

}
