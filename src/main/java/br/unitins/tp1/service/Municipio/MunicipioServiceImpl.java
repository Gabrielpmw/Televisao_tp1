package br.unitins.tp1.service.Municipio;

import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoResponseDTO;
import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioCheckDTO;
import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioRequestDTO;
import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioResponseDTO;
import br.unitins.tp1.model.Endereco.Estado;
import br.unitins.tp1.model.Endereco.Municipio;
import br.unitins.tp1.model.Endereco.Regiao;
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

    @Override
    public long count() {
        return 0;
    }

    @Override
    public long count(String nome) {
        return 0;
    }

    @Transactional
    public MunicipioResponseDTO buscarOuCadastrar(MunicipioCheckDTO dto) {
        Municipio municipioExistente = municipioRepository.findByNomeAndUf(dto.nomeCidade(), dto.uf());

        if (municipioExistente != null) {
            return MunicipioResponseDTO.valueOf(municipioExistente);
        }

        Estado estado = estadoRepository.findBySigla(dto.uf());

        if (estado == null) {
            estado = new Estado();
            estado.setNome(getNomeEstadoPorSigla(dto.uf()));
            estado.setSigla(dto.uf());
            estado.setRegiao(getRegiaoPorSigla(dto.uf()));
            estadoRepository.persist(estado);
        }

        Municipio novoMunicipio = new Municipio();
        novoMunicipio.setNome(dto.nomeCidade());
        novoMunicipio.setEstado(estado);

        municipioRepository.persist(novoMunicipio);

        return MunicipioResponseDTO.valueOf(novoMunicipio);
    }


    private Regiao getRegiaoPorSigla(String uf) {
        return switch (uf.toUpperCase()) {
            case "AM", "RR", "AP", "PA", "TO", "RO", "AC" -> Regiao.NORTE;
            case "MA", "PI", "CE", "RN", "PE", "PB", "SE", "AL", "BA" -> Regiao.NORDESTE;
            case "MT", "MS", "GO", "DF" -> Regiao.CENTRO_OESTE;
            case "SP", "RJ", "ES", "MG" -> Regiao.SUDESTE;
            case "PR", "RS", "SC" -> Regiao.SUL;
            default -> Regiao.NORTE;
        };
    }

    private String getNomeEstadoPorSigla(String uf) {
        return uf;
    }
}
