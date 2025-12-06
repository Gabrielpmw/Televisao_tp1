package br.unitins.tp1.service.Fornecedor;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.model.DTO.Fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.model.DTO.Fornecedor.FornecedorResponseDTO;
import br.unitins.tp1.model.DTO.Marca.MarcaResponseDTO;
import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.PessoaJuridica.Fabricante;
import br.unitins.tp1.model.PessoaJuridica.Fornecedor;
import br.unitins.tp1.model.Telefone;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.repository.FornecedorRepository;
import br.unitins.tp1.repository.MarcaRepository;
import br.unitins.tp1.repository.TelefoneRepository;
import br.unitins.tp1.repository.TelevisaoRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
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

        Fornecedor edicao = fornecedorRepository.findById(id);

        edicao.setRazaoSocial(dto.razaoSocial());
        edicao.setEmail(dto.email());
        edicao.setCnpj(dto.cnpj());

        edicao.getTelefones().clear();


        telefoneRepository.flush(); // <-- ESTA É A LINHA QUE FALTAVA

        if (dto.telefones() != null) { // Boa prática adicionar essa verificação
            for (TelefoneRequestDTO telefoneDTO : dto.telefones()){
                Telefone telefone = new Telefone();

                telefone.setNumero(telefoneDTO.numero());
                telefone.setDdd(telefoneDTO.ddd());
                telefone.setFornecedor(edicao); // Aponta o telefone para o fornecedor

                edicao.getTelefones().add(telefone); // Adiciona na lista
            }
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
    public List<FornecedorResponseDTO> findAll(int page, int pageSize) {
        return fornecedorRepository.findAll().page(page, pageSize).list().stream()
                .map(f -> FornecedorResponseDTO.valueOf(f)).toList();
    }

    @Override
    public List<MarcaResponseDTO> findMarcaByFornecedor(long idFornecedor) {
        return fornecedorRepository.findMarcasByFornecedor(idFornecedor).stream().map(MarcaResponseDTO::valueOf).toList();
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

    @Override
    public long count() {
        return fornecedorRepository.findAll().count();
    }

    @Override
    public long count(String razaoSocial) {
        return fornecedorRepository.count("UPPER(razaoSocial) LIKE ?1", "%" + razaoSocial.toUpperCase() + "%");
    }

    @Override
    public List<FornecedorResponseDTO> findByNome(String nome, int page, int pageSize) {
        PanacheQuery<Fornecedor> query = fornecedorRepository.findByNome(nome)
                .page(Page.of(page, pageSize));

        return query.list().stream()
                .map(FornecedorResponseDTO::valueOf)
                .toList();
    }
}