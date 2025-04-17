package br.unitins.tp1.repository;

import br.unitins.tp1.model.Televisao.Dimensao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DimensaoRepository implements PanacheRepository<Dimensao> {
}
