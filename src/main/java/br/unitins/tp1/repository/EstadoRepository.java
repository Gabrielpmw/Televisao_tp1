package br.unitins.tp1.repository;

import br.unitins.tp1.model.Endereco.Estado;
import br.unitins.tp1.model.Endereco.Municipio;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EstadoRepository implements PanacheRepository<Estado> {

    public Estado findEstadoByNome(String nome) {
        return find("UPPER(nome) = UPPER(?1)", nome).firstResult();
    }


    public List<Municipio> findMunicipiosByEstadoId(Long idEstado) {
        return getEntityManager()
                .createQuery("FROM Municipio m WHERE m.estado.id = :idEstado", Municipio.class)
                .setParameter("idEstado", idEstado)
                .getResultList();
    }

    public Estado findBySigla(String sigla) {
        if (sigla == null) return null;
        return find("UPPER(sigla) = UPPER(?1)", sigla).firstResult();
    }
}
