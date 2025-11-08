package br.unitins.tp1.repository;

import br.unitins.tp1.model.Modelo;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List; // Importar java.util.List

@ApplicationScoped
public class ModeloRepository implements PanacheRepository<Modelo> {

    public PanacheQuery<Modelo> findByNome(String nomeModelo) {
        if (nomeModelo == null || nomeModelo.isBlank()) {
            return find("1 = 1"); // retorna todos os modelos
        }
        return find("UPPER(modelo) LIKE ?1", "%" + nomeModelo.toUpperCase() + "%");
    }

    public List<Modelo> findByMarca(Long idMarca) {
        if (idMarca == null) {
            return List.of(); // Retorna lista vazia se o ID for nulo
        }
        // "marca.id" é a consulta HQL para acessar o ID da entidade Marca
        return find("marca.id", idMarca).list();
    }
}