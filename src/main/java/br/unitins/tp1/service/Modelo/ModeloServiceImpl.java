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
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

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
    public List<ModeloResponseDTO> findAll() {
        return modeloRepository.findAll().list().stream().map(ModeloResponseDTO::valueOf).toList();
    }

    @Override
    public long count() {
        return modeloRepository.findAll().count();
    }

    @Override
    public long count(String nome) {
        return 0;
    }
}
