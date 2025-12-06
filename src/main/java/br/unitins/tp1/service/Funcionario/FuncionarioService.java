package br.unitins.tp1.service.Funcionario;

import br.unitins.tp1.model.DTO.Funcionario.FuncionarioDeleteRequestDTO;
import br.unitins.tp1.model.DTO.Funcionario.FuncionarioRequestDTO;
import br.unitins.tp1.model.DTO.Funcionario.FuncionarioResponseDTO;

import java.util.List;

public interface FuncionarioService {
    FuncionarioResponseDTO create(FuncionarioRequestDTO dto);
    void update(long id, FuncionarioRequestDTO dto);
    FuncionarioResponseDTO findById(long id);
    //FuncionarioResponseDTO findByUsername(String username);
    List<FuncionarioResponseDTO> findAll();
}
