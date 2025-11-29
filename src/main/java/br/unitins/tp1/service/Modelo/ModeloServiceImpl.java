package br.unitins.tp1.service.Modelo;

import br.unitins.tp1.model.CaracteristicasGerais;
import br.unitins.tp1.model.DTO.CaracteristicasGerais.CaracteristicasResponseDTO;
import br.unitins.tp1.model.DTO.Modelo.ModeloRequestDTO;
import br.unitins.tp1.model.DTO.Modelo.ModeloResponseDTO;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.Modelo;
import br.unitins.tp1.repository.CaracteristicasGeraisRepository;
import br.unitins.tp1.repository.MarcaRepository;
import br.unitins.tp1.repository.ModeloRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
// 'stream' e 'Collectors' podem já estar implícitos, mas é bom garantir
import java.util.stream.Collectors;


@ApplicationScoped
public class ModeloServiceImpl implements ModeloService{


    @Inject
    ModeloRepository modeloRepository;

    @Inject
    MarcaRepository marcaRepository;
    @Inject
    CaracteristicasGeraisRepository caracteristicasGeraisRepository;

    @Override
    @Transactional
    public ModeloResponseDTO create(ModeloRequestDTO dto) {
        Modelo modelo = new Modelo();
        CaracteristicasGerais caracteristicasGerais = caracteristicasGeraisRepository.findById(dto.idCaracteristicas());

        modelo.setModelo(dto.modelo());
        modelo.setMesesGarantia(dto.mesesGarantia());
        modelo.setAnoLancamento(dto.anoLancamento());

        modelo.setCaracteristicas(caracteristicasGerais);

        Marca marca = marcaRepository.findById(dto.idMarca());

        modelo.setMarca(marca);

        modeloRepository.persist(modelo);

        return ModeloResponseDTO.valueOf(modelo);
    }

    @Override
    @Transactional
    public CaracteristicasResponseDTO caracteristicaForModelo(long idModelo, long idCaracteristica) {
        Modelo modelo = modeloRepository.findById(idModelo);
        CaracteristicasGerais caracteristicasGerais = caracteristicasGeraisRepository.findById(idCaracteristica);

        modelo.setCaracteristicas(caracteristicasGerais);

        return CaracteristicasResponseDTO.valueOf(caracteristicasGerais);
    }

    @Override
    @Transactional
    public void update(long id, ModeloRequestDTO dto) {
        Modelo modelo = modeloRepository.findById(id);
        Marca marca = marcaRepository.findById(dto.idMarca());
        CaracteristicasGerais caracteristicasGerais = caracteristicasGeraisRepository.findById(dto.idCaracteristicas());

        modelo.setModelo(dto.modelo());
        modelo.setMesesGarantia(dto.mesesGarantia());
        modelo.setAnoLancamento(dto.anoLancamento());
        modelo.setMarca(marca);
        modelo.setCaracteristicas(caracteristicasGerais);
    }

    @Override
    @Transactional
    public void delete(long id) {
        modeloRepository.deleteById(id);
    }

    @Override
    public ModeloResponseDTO findById(long id) {
        return ModeloResponseDTO.valueOf(modeloRepository.findById(id));
    }

    @Override
    public List<ModeloResponseDTO> findAll(int page, int pageSize) {
        return modeloRepository.findAll().page(page, pageSize).list().stream()
                .map(ModeloResponseDTO::valueOf).toList();
    }

    @Override
    public List<ModeloResponseDTO> findByNome(String nome, int page, int pageSize) {
        PanacheQuery<Modelo> query = modeloRepository.findByNome(nome)
                .page(Page.of(page, pageSize));

        return query.list().stream()
                .map(ModeloResponseDTO::valueOf)
                .toList();
    }

    // --- NOVO MÉTODO IMPLEMENTADO ---
    @Override
    public List<ModeloResponseDTO> findByMarca(Long idMarca) {
        // 1. Chama o método do repositório
        // (Lembre-se de adicionar 'findByMarca' ao seu ModeloRepository.java)
        List<Modelo> modelos = modeloRepository.findByMarca(idMarca);

        // 2. Converte a lista de Entidades para DTOs, seguindo seu padrão
        return modelos.stream()
                .map(ModeloResponseDTO::valueOf)
                .toList(); // ou .collect(Collectors.toList());
    }
    // --- FIM DO NOVO MÉTODO ---

    @Override
    public long count() {
        return modeloRepository.findAll().count();
    }

    @Override
    public long count(String nome) {
        return modeloRepository.findByNome(nome).count();
    }
}