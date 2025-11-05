package br.unitins.tp1.service.Marca;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.model.DTO.Marca.MarcaRequestDTO;
import br.unitins.tp1.model.DTO.Marca.MarcaResponseDTO;
import br.unitins.tp1.model.DTO.Modelo.ModeloResponseDTO;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.PessoaJuridica.Fabricante;
import br.unitins.tp1.repository.FabricanteRepository;
import br.unitins.tp1.repository.MarcaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class MarcaServiceImpl implements MarcaService{

    @Inject
    MarcaRepository marcaRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    @Override
    @Transactional
    public MarcaResponseDTO create(MarcaRequestDTO dto) {
        Marca marca = new Marca();
        Fabricante fabricante = fabricanteRepository.findById(dto.idFabricante());

        marca.setNomeMarca(dto.nomeMarca());
        marca.setDescricao(dto.descricao());
        marca.setFabricante(fabricante);

        marcaRepository.persist(marca);

        return MarcaResponseDTO.valueOf(marca);
    }

    @Override
    @Transactional
    public void update(long id, MarcaRequestDTO dto) {
        Marca marca = marcaRepository.findById(id);
        Fabricante fabricante = fabricanteRepository.findById(dto.idFabricante());

        marca.setNomeMarca(dto.nomeMarca());
        marca.setDescricao(dto.descricao());
        marca.setFabricante(fabricante);
    }

    @Override
    @Transactional
    public void delete(long id) {
        marcaRepository.deleteById(id);
    }

    @Override
    public MarcaResponseDTO findById(long id) {
        return MarcaResponseDTO.valueOf(marcaRepository.findById(id));
    }

    @Override
    public List<MarcaResponseDTO> findAll() {
        return marcaRepository.findAll().list().stream().map(MarcaResponseDTO::valueOf).toList();
    }

    @Override
    public List<ModeloResponseDTO> findModeloByMarca(long idMarca) {
        return marcaRepository.findModelosByMarca(idMarca).stream().map(ModeloResponseDTO::valueOf).toList();
    }

    @Override
    public long count() {
        return marcaRepository.findAll().count();
    }

    @Override
    public long count(String nome) {
        return 0;
    }
}
