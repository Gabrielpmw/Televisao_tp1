package br.unitins.tp1.service.Endereco;

import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoRequestDTO;
import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoResponseDTO;
import br.unitins.tp1.model.DTO.Endereco.Estado.EstadoResponseDTO;
import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioResponseDTO;
import br.unitins.tp1.model.Endereco.Endereco;
import br.unitins.tp1.model.Endereco.Municipio;
import br.unitins.tp1.repository.EnderecoRepository;
import br.unitins.tp1.repository.EstadoRepository;
import br.unitins.tp1.repository.MunicipioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService{

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    EstadoRepository estadoRepository;

    @Override
    @Transactional
    public EnderecoResponseDTO create(EnderecoRequestDTO dto) {
        Municipio municipio = municipioRepository.findById(dto.idMunicipio());

        Endereco endereco = new Endereco();
        endereco.setCep(dto.cep());
        endereco.setBairro(dto.bairro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setMunicipio(municipio);

        enderecoRepository.persist(endereco);

        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    @Transactional
    public void update(long id, EnderecoRequestDTO dto) {
        Endereco endereco = enderecoRepository.findById(id);

        Municipio municipio = municipioRepository.findById(dto.idMunicipio());

        endereco.setCep(dto.cep());
        endereco.setBairro(dto.bairro());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setMunicipio(municipio);
    }

    @Override
    @Transactional
    public void delete(long id) {
        enderecoRepository.deleteById(id);
    }

    @Override
    public EnderecoResponseDTO findById(long id) {
        return EnderecoResponseDTO.valueOf(enderecoRepository.findById(id));
    }

    @Override
    public List<EnderecoResponseDTO> findAll() {
        return enderecoRepository.findAll().stream().map(EnderecoResponseDTO::valueOf).toList();
    }

    public List<EnderecoResponseDTO> findByCEP(String cep){
        return enderecoRepository.findByCep(cep).stream().map(EnderecoResponseDTO::valueOf).toList();
    }

    public MunicipioResponseDTO findByMunicipio(String cep){
        return MunicipioResponseDTO.valueOf(enderecoRepository.findMunicipioByCep(cep));
    }
}
