package br.unitins.tp1.repository;

import br.unitins.tp1.model.Fabricante;
import br.unitins.tp1.model.Televisao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class FabricanteRepository implements PanacheRepository<Fabricante> {

}
