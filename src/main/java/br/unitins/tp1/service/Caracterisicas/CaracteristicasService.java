package br.unitins.tp1.service.Caracterisicas;


import br.unitins.tp1.model.DTO.CaracteristicasGerais.CaracteristicasRequestDTO;
import br.unitins.tp1.model.DTO.CaracteristicasGerais.CaracteristicasResponseDTO;

import java.util.List;

public interface CaracteristicasService {
    CaracteristicasResponseDTO create(CaracteristicasRequestDTO dto);
    void update(long id, CaracteristicasRequestDTO dto);
    void delete(long id);
    CaracteristicasResponseDTO findById(long id);
    List<CaracteristicasResponseDTO> findAll(int page, int pageSize);
    List<CaracteristicasResponseDTO> findByNomeQuerry(String nome, int page, int pageSize);
    List<CaracteristicasResponseDTO> findaByNome(String nome);
}
