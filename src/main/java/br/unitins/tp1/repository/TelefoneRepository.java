package br.unitins.tp1.repository;

import br.unitins.tp1.model.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone> {

    public List<Telefone> findByIdsTelefone(List<Long> id) {
        return find("SELECT t FROM Telefone t WHERE t.id IN ?1", id).stream().toList();
    }
}
