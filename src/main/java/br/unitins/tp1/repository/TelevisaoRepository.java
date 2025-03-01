package br.unitins.tp1.repository;

import br.unitins.tp1.model.Televisao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TelevisaoRepository implements PanacheRepository<Televisao>{

    public Televisao findByMarca(String marca){
        return find("select t from Televisao t where t.marca = ?1", marca).firstResult();
    }

    public List<Televisao> findByPolegada(int polegada) {
        return find("select t from Televisao t where t.polegada > ?1", polegada).list();
    }
}
