package br.unitins.tp1.service.Fornecedor;

import br.unitins.tp1.model.DTO.Fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.model.DTO.Fornecedor.FornecedorResponseDTO;
import br.unitins.tp1.model.DTO.Marca.MarcaResponseDTO;
import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.PessoaJuridica.Fornecedor;
import br.unitins.tp1.model.Telefone;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.repository.FornecedorRepository;
import br.unitins.tp1.repository.MarcaRepository;
import br.unitins.tp1.repository.TelefoneRepository;
import br.unitins.tp1.repository.TelevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FornecedorServiceImpl implements FornecedorService {
    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    TelevisaoRepository televisaoRepository;

    @Inject
    MarcaRepository marcaRepository;

    @Override
    @Transactional
    public FornecedorResponseDTO create(FornecedorRequestDTO dto) {
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setRazaoSocial(dto.razaoSocial());
        fornecedor.setEmail(dto.email());
        fornecedor.setStatus(dto.status());
        fornecedor.setCnpj(dto.cnpj());

        fornecedorRepository.persist(fornecedor);
        for (TelefoneRequestDTO request : dto.telefones()){
            Telefone telefone = new Telefone();
            telefone.setDdd(request.ddd());
            telefone.setNumero(request.numero());
            telefone.setFornecedor(fornecedor);
            fornecedor.getTelefones().add(telefone);
            telefoneRepository.persist(telefone);
        }

        return FornecedorResponseDTO.valueOf(fornecedor);
    }

    @Override
    @Transactional
    public void update(long id, FornecedorRequestDTO dto) {
        Fornecedor novoFornecedor = fornecedorRepository.findById(id);

        novoFornecedor.setRazaoSocial(dto.razaoSocial());
        novoFornecedor.setEmail(dto.email());
        novoFornecedor.setStatus(dto.status());
        novoFornecedor.setCnpj(dto.cnpj());

        novoFornecedor.getTelefones().clear();
        for (TelefoneRequestDTO telefoneDTO : dto.telefones()){
            Telefone telefone = new Telefone();

            telefone.setNumero(telefoneDTO.numero());
            telefone.setDdd(telefoneDTO.ddd());
            telefone.setFornecedor(novoFornecedor);

            novoFornecedor.getTelefones().add(telefone);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        fornecedorRepository.deleteById(id);
    }

    @Override
    public FornecedorResponseDTO findById(long id) {
        return FornecedorResponseDTO.valueOf(fornecedorRepository.findById(id));
    }

    @Override
    @Transactional
    public List<FornecedorResponseDTO> findAll() {
        return fornecedorRepository.findAll().stream().map(f -> FornecedorResponseDTO.valueOf(f)).toList();
    }

    @Override
    public List<TelevisaoResponseDTO> findTelevisaoByFornecedor(long idFornecedor) {
        List<Televisao> televisaos = fornecedorRepository.findTelevisaoByFornecedor(idFornecedor);

        return televisaos.stream().map(TelevisaoResponseDTO::valueOf).toList();
    }

    @Override
    public FornecedorResponseDTO findFornecedorByTelefone(long idTelefone) {
        return FornecedorResponseDTO.valueOf(fornecedorRepository.findFornecedorByTelefone(idTelefone));
    }

    @Override
    @Transactional
    public List<MarcaResponseDTO> marcaForFornecedor(long idFornecedor, List<Long> idMarcas) {
        Fornecedor fornecedor = fornecedorRepository.findById(idFornecedor);

        for (Long idMarca : idMarcas){
            Marca marca = marcaRepository.findById(idMarca);
            fornecedor.getMarcas().add(marca);
        }

        List<MarcaResponseDTO> response = new ArrayList<>();
        for (Marca marca : fornecedor.getMarcas()){
            response.add(MarcaResponseDTO.valueOf(marca));
        }

        return response;
    }
}
