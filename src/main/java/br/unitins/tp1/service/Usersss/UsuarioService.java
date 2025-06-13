package br.unitins.tp1.service.Usersss;

import br.unitins.tp1.model.DTO.Usuario.UsuarioCreateRequestDTO;
import br.unitins.tp1.model.DTO.Usuario.UsuarioResponseDTO;
import br.unitins.tp1.model.DTO.Usuario.UsuarioUpdateRequestDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioResponseDTO create(UsuarioCreateRequestDTO dto);

    void update(UsuarioUpdateRequestDTO dto);

    void delete(long id);

    UsuarioResponseDTO findById(long id);

    List<UsuarioResponseDTO> findAll();

    UsuarioResponseDTO findByUsername(String username);
}
