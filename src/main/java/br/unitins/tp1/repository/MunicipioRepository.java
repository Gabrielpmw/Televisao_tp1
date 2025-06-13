package br.unitins.tp1.repository;

import br.unitins.tp1.model.Endereco.Endereco;
import br.unitins.tp1.model.Endereco.Municipio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MunicipioRepository implements PanacheRepository<Municipio> {

    public List<Endereco> findEnderecosByMunicipioId(Long idMunicipio) {
        return getEntityManager()
                .createQuery("SELECT e FROM Endereco e WHERE e.municipio.id = :id", Endereco.class)
                .setParameter("id", idMunicipio)
                .getResultList();
    }
}
