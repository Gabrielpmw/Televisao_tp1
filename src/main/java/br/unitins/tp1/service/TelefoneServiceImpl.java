package br.unitins.tp1.service;

import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
import br.unitins.tp1.model.DTO.Telefone.TelefoneResponseDTO;
import br.unitins.tp1.model.Telefone;
import br.unitins.tp1.repository.TelefoneRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TelefoneServiceImpl implements TelefoneService{
    @Inject
    TelefoneRepository repository;

    @Override
    @Transactional
    public TelefoneResponseDTO create(TelefoneRequestDTO dto) {
        Telefone telefone = new Telefone();

        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

        repository.persist(telefone);

        return TelefoneResponseDTO.valueOf(telefone);
    }

    @Override
    @Transactional
    public void update(long id, TelefoneRequestDTO dto) {
        Telefone telefone = repository.findById(id);

        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());
    }

    @Override
    @Transactional
    public void delete(long id) {
        repository.deleteById(id);
    }

    @Override
    public TelefoneResponseDTO findById(long id) {
        return TelefoneResponseDTO.valueOf(repository.findById(id));
    }

    @Override
    public List<TelefoneResponseDTO> findAll() {
        return repository.findAll().stream().map(t -> TelefoneResponseDTO.valueOf(t)).toList();
    }
}
