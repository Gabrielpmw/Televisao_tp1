package br.unitins.tp1.service.Fornecedor;

import br.unitins.tp1.model.DTO.Fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.model.DTO.Fornecedor.FornecedorResponseDTO;
import br.unitins.tp1.model.DTO.Telefone.TelefoneResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Fornecedor;
import br.unitins.tp1.model.Telefone;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.repository.FornecedorRepository;
import br.unitins.tp1.repository.TelefoneRepository;
import br.unitins.tp1.repository.TelevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class FornecedorServiceImpl implements FornecedorService {
    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    TelevisaoRepository televisaoRepository;

    @Override
    @Transactional
    public FornecedorResponseDTO create(FornecedorRequestDTO dto) {
        Fornecedor fornecedor = new Fornecedor();

        fornecedor.setNome(dto.nome());

        fornecedorRepository.persist(fornecedor);

        List<Telefone> telefones = dto.idTelefone().stream()
                .map(telefoneRepository::findById)
                .filter(Objects::nonNull)
                .toList();

        for (Telefone telefone : telefones){
            telefone.setFornecedor(fornecedor);
            fornecedor.getTelefones().add(telefone);
        }

        List<Televisao> televisoes = televisaoRepository.findByIds(dto.idTelevisao());

        fornecedor.setTelevisaos(televisoes);

        return FornecedorResponseDTO.valueOf(fornecedor);
    }

    @Override
    @Transactional
    public void update(long id, FornecedorRequestDTO dto) {
        Fornecedor novoFornecedor = fornecedorRepository.findById(id);

        novoFornecedor.setNome(dto.nome());

        novoFornecedor.getTelefones().clear();

        List<Telefone> novosTelefones = dto.idTelefone().stream()
                .map(telefoneRepository::findById)
                .filter(Objects::nonNull)
                .toList();


        for (Telefone telefone : novosTelefones){
            telefone.setFornecedor(novoFornecedor);
            novoFornecedor.getTelefones().add(telefone);
        }

        List<Televisao> novasTelevisoes = televisaoRepository.findByIds(dto.idTelevisao());
        novoFornecedor.setTelevisaos(novasTelevisoes);
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
    @Transactional
    public List<TelevisaoResponseDTO> findTelevisaoByFornecedor(long id) {
        return fornecedorRepository.findTelevisoesByFornecedor(id)
                .stream()
                .map(TelevisaoResponseDTO::valueOf)
                .toList();
    }
}
