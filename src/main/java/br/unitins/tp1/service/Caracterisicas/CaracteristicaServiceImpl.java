package br.unitins.tp1.service.Caracterisicas;

import br.unitins.tp1.model.CaracteristicasGerais;
import br.unitins.tp1.model.DTO.CaracteristicasGerais.CaracteristicasRequestDTO;
import br.unitins.tp1.model.DTO.CaracteristicasGerais.CaracteristicasResponseDTO;
import br.unitins.tp1.repository.CaracteristicasGeraisRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CaracteristicaServiceImpl implements CaracteristicasService {

    @Inject
    CaracteristicasGeraisRepository caracteristicasGeraisRepository;

    @Override
    @Transactional
    public CaracteristicasResponseDTO create(CaracteristicasRequestDTO dto) {
        CaracteristicasGerais caracteristicasGerais = new CaracteristicasGerais();

        caracteristicasGerais.setNome(dto.nome());
        caracteristicasGerais.setSistemaOperacional(dto.sistemaOperacional());
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

        caracteristicas.setNome(dto.nome());
        caracteristicas.setSistemaOperacional(dto.sistemaOperacional());
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
    public List<CaracteristicasResponseDTO> findAll(int page, int pageSize) {
        return caracteristicasGeraisRepository.findAll()
                .page(page, pageSize)
                .list().stream()
                .map(CaracteristicasResponseDTO::valueOf)
                .toList();
    }

    public long count() {
        return caracteristicasGeraisRepository.findAll().count();
    }

    @Override
    public List<CaracteristicasResponseDTO> findByNomeQuerry(String nome, int page, int pageSize) {
        PanacheQuery<CaracteristicasGerais> query = caracteristicasGeraisRepository.findByNomeQuerry(nome)
                .page(Page.of(page, pageSize));

        return query.list().stream()
                .map(CaracteristicasResponseDTO::valueOf)
                .toList();
    }

    public long count(String nome) {
        return caracteristicasGeraisRepository.findByNomeQuerry(nome).count();
    }


    @Override
    public List<CaracteristicasResponseDTO> findaByNome(String nome) {
        return caracteristicasGeraisRepository.findByNome(nome).stream().map(CaracteristicasResponseDTO::valueOf).toList();
    }
}