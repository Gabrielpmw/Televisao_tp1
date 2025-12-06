package br.unitins.tp1.repository;

import br.unitins.tp1.model.Televisao.Dimensao;
import br.unitins.tp1.model.Televisao.Televisao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class DimensaoRepository implements PanacheRepository<Dimensao> {

    public List<Televisao> findTelevisaoByDimensao(long idDimensao){
        return find("SELECT t FROM Televisao t WHERE t.dimensao.id = ?1", idDimensao)
                .project(Televisao.class)
                .list();
    }
}
