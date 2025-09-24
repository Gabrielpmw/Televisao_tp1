package br.unitins.tp1.repository;

import br.unitins.tp1.model.Modelo;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ModeloRepository implements PanacheRepository<Modelo> {
}
