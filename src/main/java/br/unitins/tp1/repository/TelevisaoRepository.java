package br.unitins.tp1.repository;

import br.unitins.tp1.model.Televisao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TelevisaoRepository implements PanacheRepository<Televisao>{

    public Televisao findByMarca(String marca){
        return find("from Televisao t where t.marca = ?1", marca).firstResult();
    }


    public List<Televisao> findByModelo(String modelo){
        return find("from Televisao t where t.modelo =?1", modelo).list();
    }
}
