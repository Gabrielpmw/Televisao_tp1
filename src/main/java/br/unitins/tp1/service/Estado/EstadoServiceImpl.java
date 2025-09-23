package br.unitins.tp1.service.Estado;

import br.unitins.tp1.model.DTO.Endereco.Estado.EstadoRequestDTO;
import br.unitins.tp1.model.DTO.Endereco.Estado.EstadoResponseDTO;
import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioResponseDTO;
import br.unitins.tp1.model.Endereco.Estado;
import br.unitins.tp1.model.Endereco.Regiao;
import br.unitins.tp1.repository.EstadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService{
    @Inject
    EstadoRepository estadoRepository;

    @Override
    @Transactional
    public EstadoResponseDTO create(EstadoRequestDTO dto) {
        Estado estado = new Estado();

        estado.setNome(dto.estado());
        estado.setSigla(dto.sigla());
        estado.setRegiao(Regiao.valueOf(dto.idRegiao()));

        estadoRepository.persist(estado);

        return EstadoResponseDTO.valueOf(estado);
    }

    @Override
    @Transactional
    public void update(long id, EstadoRequestDTO dto) {
        Estado estado = estadoRepository.findById(id);

        estado.setNome(dto.estado());
        estado.setSigla(dto.sigla());
    }

    @Override
    @Transactional
    public void delete(long id) {
        estadoRepository.deleteById(id);
    }

    @Override
    public EstadoResponseDTO findById(long id) {
        return EstadoResponseDTO.valueOf(estadoRepository.findById(id));
    }

    @Override
    public List<EstadoResponseDTO> findAll() {
        return estadoRepository.findAll().stream().map(EstadoResponseDTO::valueOf).toList();
    }

    @Override
    public EstadoResponseDTO findByNome(String nome) {
        return EstadoResponseDTO.valueOf(estadoRepository.findEstadoByNome(nome));
    }

    @Override
    public List<MunicipioResponseDTO> findMunicipioByEstado(long idEstado) {
        return estadoRepository.findMunicipiosByEstadoId(idEstado).stream().map(MunicipioResponseDTO::valueOf).toList();
    }
}
