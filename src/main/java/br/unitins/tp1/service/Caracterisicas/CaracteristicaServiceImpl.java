package br.unitins.tp1.service.Caracterisicas;

import br.unitins.tp1.model.CaracteristicasGerais;
import br.unitins.tp1.model.DTO.CaracteristicasGerais.CaracteristicasRequestDTO;
import br.unitins.tp1.model.DTO.CaracteristicasGerais.CaracteristicasResponseDTO;
import br.unitins.tp1.repository.CaracteristicasGeraisRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CaracteristicaServiceImpl implements CaracteristicasService{

    @Inject
    CaracteristicasGeraisRepository caracteristicasGeraisRepository;

    @Override
    @Transactional
    public CaracteristicasResponseDTO create(CaracteristicasRequestDTO dto) {
        CaracteristicasGerais caracteristicasGerais = new CaracteristicasGerais();


        caracteristicasGerais.setSistemaOperacioanl(dto.sistemaOperacional());
        caracteristicasGerais.setQuantidadeHDMI(dto.quantidadeHDMI());
        caracteristicasGerais.setQuantidadeUSB(dto.quantidadeUSB());
        caracteristicasGerais.setSmartTV(dto.smartTV());

        caracteristicasGeraisRepository.persist(caracteristicasGerais);

        return CaracteristicasResponseDTO.valueOf(caracteristicasGerais);
    }

    @Override
    @Transactional
    public void update(long id, CaracteristicasRequestDTO dto) {
        CaracteristicasGerais caracteristicas = caracteristicasGeraisRepository.findById(id);

        caracteristicas.setSistemaOperacioanl(dto.sistemaOperacional());
        caracteristicas.setQuantidadeHDMI(dto.quantidadeHDMI());
        caracteristicas.setQuantidadeUSB(dto.quantidadeUSB());
        caracteristicas.setSmartTV(dto.smartTV());
    }

    @Override
    @Transactional
    public void delete(long id) {
        caracteristicasGeraisRepository.deleteById(id);
    }

    @Override
    public CaracteristicasResponseDTO findById(long id) {
        return CaracteristicasResponseDTO.valueOf(caracteristicasGeraisRepository.findById(id));
    }

    @Override
    public List<CaracteristicasResponseDTO> findAll() {
        return caracteristicasGeraisRepository.findAll().stream().map(CaracteristicasResponseDTO::valueOf).toList();
    }
}
