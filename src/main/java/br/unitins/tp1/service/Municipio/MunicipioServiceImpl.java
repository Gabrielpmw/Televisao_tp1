package br.unitins.tp1.service.Municipio;

import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoResponseDTO;
import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioRequestDTO;
import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioResponseDTO;
import br.unitins.tp1.model.Endereco.Estado;
import br.unitins.tp1.model.Endereco.Municipio;
import br.unitins.tp1.repository.EstadoRepository;
import br.unitins.tp1.repository.MunicipioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class MunicipioServiceImpl implements MunicipioService{

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    EstadoRepository estadoRepository;

    @Override
    @Transactional
    public MunicipioResponseDTO create(MunicipioRequestDTO dto) {
        Municipio municipio = new Municipio();

        municipio.setNome(dto.municipio());

        Estado estado = estadoRepository.findById(dto.idEstado());

        municipio.setEstado(estado);

        municipioRepository.persist(municipio);

        return MunicipioResponseDTO.valueOf(municipio);
    }

    @Override
    @Transactional
    public void update(long id, MunicipioRequestDTO dto) {
        Municipio municipio = municipioRepository.findById(id);
        Estado estado = estadoRepository.findById(dto.idEstado());

        municipio.setNome(dto.municipio());
        municipio.setEstado(estado);

    }

    @Override
    @Transactional
    public void delete(long id) {
        municipioRepository.deleteById(id);
    }

    @Override
    public MunicipioResponseDTO findById(long id) {
       Municipio municipio = municipioRepository.findById(id);

       return MunicipioResponseDTO.valueOf(municipio);
    }

    @Override
    public List<MunicipioResponseDTO> findAll() {
        return municipioRepository.findAll().stream().map(MunicipioResponseDTO::valueOf).toList();
    }

    @Override
    public List<EnderecoResponseDTO> findEnderecoByMunicipio(long id) {
        return municipioRepository.findEnderecosByMunicipioId(id).stream().map(EnderecoResponseDTO::valueOf).toList();
    }
}
