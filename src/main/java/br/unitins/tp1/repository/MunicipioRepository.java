package br.unitins.tp1.repository;

import br.unitins.tp1.model.Endereco.Municipio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MunicipioRepository implements PanacheRepository<Municipio> {

    public List<Municipio> findAllOrderByNomeDesc() {
        return list("ORDER BY nome DESC");
    }

}
