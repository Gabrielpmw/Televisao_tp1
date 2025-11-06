package br.unitins.tp1.repository;

import br.unitins.tp1.model.Modelo;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModeloRepository implements PanacheRepository<Modelo> {
    public PanacheQuery<Modelo> findByNome(String nomeModelo) {
        if (nomeModelo == null || nomeModelo.isBlank()) {
            return find("1 = 1"); // retorna todos os modelos
        }
        return find("UPPER(modelo) LIKE ?1", "%" + nomeModelo.toUpperCase() + "%");
    }
}
