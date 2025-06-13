package br.unitins.tp1.repository;

import br.unitins.tp1.model.Endereco.Endereco;
import br.unitins.tp1.model.Endereco.Municipio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    public List<Endereco> findByCep(String cep) {
        return list("cep", cep);
    }

    public Municipio findMunicipioByCep(String cep) {
        return find("SELECT e.municipio FROM Endereco e WHERE e.cep = ?1", cep)
                .project(Municipio.class)
                .firstResult();
    }
}
