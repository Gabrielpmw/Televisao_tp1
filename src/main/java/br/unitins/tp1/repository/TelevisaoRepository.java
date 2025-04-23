package br.unitins.tp1.repository;

import br.unitins.tp1.model.Televisao.Televisao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
public class TelevisaoRepository implements PanacheRepository<Televisao>{

    public List<Televisao> findByIds(List<Long> id){
        return find("SELECT t from Televisao t WHERE t.id in ?1", id).stream().toList();
    }

    public Televisao findByMarca(String marca){
        return find("from Televisao t where t.marca = ?1", marca).firstResult();
    }

    public List<Televisao> findByModelo(String modelo){
        return find("from Televisao t where t.modelo =?1", modelo).list();
    }

    public List<Televisao> findByFabricante(long idFabricante){
        return find("fabricante.id", idFabricante).list();
    }
}
