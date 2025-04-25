package br.unitins.tp1.repository;

import br.unitins.tp1.model.Endereco.Estado;
import br.unitins.tp1.model.Endereco.Municipio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EstadoRepository implements PanacheRepository<Estado> {

    public Estado findEstadoByNome(String nome) {
        return find("UPPER(nome) = UPPER(?1)", nome).firstResult();
    }

    public List<Estado> listarTodosOrdenadosPorNome() {
        return list("ORDER BY nome ASC");
    }
}
