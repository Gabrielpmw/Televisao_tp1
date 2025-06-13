package br.unitins.tp1.service.Fabricante;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteRequestDTO;
import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.model.DTO.Telefone.TelefoneResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;

import java.util.List;

public interface FabricanteService {
    FabricanteResponseDTO create(FabricanteRequestDTO dto);
    void update(long id, FabricanteRequestDTO dto);
    void delete(long id);
    FabricanteResponseDTO findById(long id);
    List<FabricanteResponseDTO> findAll();
    List<TelevisaoResponseDTO> findTelevisaoByFabricante(long idFabricante);
    FabricanteResponseDTO findByNome(String nome);
}
