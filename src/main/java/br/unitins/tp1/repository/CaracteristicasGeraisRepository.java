package br.unitins.tp1.repository;

import br.unitins.tp1.model.CaracteristicasGerais;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class CaracteristicasGeraisRepository implements PanacheRepository<CaracteristicasGerais> {
    public PanacheQuery<CaracteristicasGerais> findByNomeQuerry(String nome) {
        if (nome == null || nome.isBlank()) {
            return findAll();
        }
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

    public List<CaracteristicasGerais> findByNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return listAll();
        }
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }
}
