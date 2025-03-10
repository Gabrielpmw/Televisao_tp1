package br.unitins.tp1.repository;

import br.unitins.tp1.model.Televisao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TelevisaoRepository implements PanacheRepository<Televisao>{

    public List<Televisao> findByMarca(String marca){
        return find("select t from Televisao t where t.marca = ?1", marca).firstResult();
    }


}
