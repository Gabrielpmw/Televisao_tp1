package br.unitins.tp1.service.Fabricante;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteRequestDTO;
import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.model.DTO.Marca.MarcaResponseDTO;
import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.PessoaJuridica.Fabricante;
import br.unitins.tp1.model.PessoaJuridica.Fornecedor;
import br.unitins.tp1.model.Telefone;
import br.unitins.tp1.repository.FabricanteRepository;
import br.unitins.tp1.repository.MarcaRepository;
import br.unitins.tp1.repository.TelefoneRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FabricanteServiceImpl implements FabricanteService {
    @Inject
    FabricanteRepository fabricanteRepository;
    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    MarcaRepository marcaRepository;

    @Override
    @Transactional
    public FabricanteResponseDTO create(FabricanteRequestDTO dto) {
        Fabricante fabricante = new Fabricante();

        fabricante.setRazaoSocial(dto.razaoSocial());
        fabricante.setAnoFundacao(dto.dataAbertura());
        fabricante.setCnpj(dto.cnpj());
        fabricante.setStatus(dto.status());
        fabricante.setPaisSede(dto.paisSede());

        fabricanteRepository.persist(fabricante);

        for (TelefoneRequestDTO requestDTO : dto.telefones()) {
            Telefone telefone = new Telefone();
            telefone.setDdd(requestDTO.ddd());
            telefone.setNumero(requestDTO.numero());
            telefone.setFabricante(fabricante);
            fabricante.getTelefones().add(telefone);

            telefoneRepository.persist(telefone);
        }


        return FabricanteResponseDTO.valueOf(fabricante);
    }


    @Override
    @Transactional
    public void update(long id, FabricanteRequestDTO dto) {
        Fabricante edicao = fabricanteRepository.findById(id);

        edicao.setRazaoSocial(dto.razaoSocial());
        edicao.setAnoFundacao(dto.dataAbertura());
        edicao.setCnpj(dto.cnpj());
        edicao.setStatus(dto.status());
        edicao.setPaisSede(dto.paisSede());

        edicao.getTelefones().clear();
        for (TelefoneRequestDTO requestDTO : dto.telefones()) {
            Telefone telefone = new Telefone();
            telefone.setDdd(requestDTO.ddd());
            telefone.setNumero(requestDTO.numero());
            telefone.setFabricante(edicao);
            edicao.getTelefones().add(telefone);
        }
    }


    @Override
    @Transactional
    public void delete(long id) {
        fabricanteRepository.deleteById(id);
    }

    @Override
    public FabricanteResponseDTO findById(long id) {
        return FabricanteResponseDTO.valueOf(fabricanteRepository.findById(id));
    }

    @Transactional
    @Override
    public List<FabricanteResponseDTO> findAll() {
        return fabricanteRepository.findAll().stream().map(FabricanteResponseDTO::valueOf).toList();
    }

    @Override
    public List<MarcaResponseDTO> findMarcasByFabricante(long idFabricante) {
        return fabricanteRepository.findMarcasByFabricante(idFabricante).stream().map(MarcaResponseDTO::valueOf).toList();
    }

    @Override
    public FabricanteResponseDTO findByNome(String nome) {
        Fabricante fabricante = fabricanteRepository.findByNome(nome);
        return FabricanteResponseDTO.valueOf(fabricante);
    }

}
