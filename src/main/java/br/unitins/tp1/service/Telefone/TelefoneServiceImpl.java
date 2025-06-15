package br.unitins.tp1.service.Telefone;

import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
import br.unitins.tp1.model.DTO.Telefone.TelefoneResponseDTO;
import br.unitins.tp1.model.Telefone;
import br.unitins.tp1.repository.TelefoneRepository;
import br.unitins.tp1.validation.ValidationException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TelefoneServiceImpl implements TelefoneService{
    @Inject
    TelefoneRepository telefoneRepository;

    @Override
    @Transactional
    public TelefoneResponseDTO create(TelefoneRequestDTO dto) {
        Telefone telefone = new Telefone();

        if (dto ==  null){
            throw new ValidationException("DTO", "DTO não pode ser null");
        }

        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());

        telefoneRepository.persist(telefone);

        return TelefoneResponseDTO.valueOf(telefone);
    }

    @Override
    @Transactional
    public void update(long id, TelefoneRequestDTO dto) {
        Telefone telefone = telefoneRepository.findById(id);
        if (dto ==  null){
            throw new ValidationException("DTO", "DTO não pode ser null");
        }

        if (telefone == null){
            throw new ValidationException("Telefone", "Telefone não encontrado");
        }

        telefone.setDdd(dto.ddd());
        telefone.setNumero(dto.numero());
    }

    @Override
    @Transactional
    public void delete(long id) {
        Telefone telefone = telefoneRepository.findById(id);
        if (telefone == null){
            throw new ValidationException("Telefone", "Telefone não encontrado");
        }

        telefoneRepository.deleteById(id);
    }

    @Override
    public TelefoneResponseDTO findById(long id) {
        Telefone telefone = telefoneRepository.findById(id);
        if (telefone == null){
            throw new ValidationException("Telefone", "Telefone não encontrado");
        }

        return TelefoneResponseDTO.valueOf(telefoneRepository.findById(id));
    }

    @Override
    public List<TelefoneResponseDTO> findAll() {
        return telefoneRepository.findAll().stream().map(t -> TelefoneResponseDTO.valueOf(t)).toList();
    }

    @Override
    public List<TelefoneResponseDTO> findTelefonesByDDD(String ddd) {
        return telefoneRepository.findTelefonesByDdd(ddd).stream().map(TelefoneResponseDTO::valueOf).toList();
    }
}
