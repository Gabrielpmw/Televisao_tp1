package br.unitins.tp1.repository;

import br.unitins.tp1.model.Televisao.Televisao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


import java.util.List;

@ApplicationScoped
public class TelevisaoRepository implements PanacheRepository<Televisao> {

    public List<Televisao> findByIds(List<Long> id) {
        return find("SELECT t from Televisao t WHERE t.id in ?1", id).stream().toList();
    }

    public Televisao findTelevisaoByModelo(String modelo) {
        // Correto: "procure pelo campo 'modelo' DENTRO do objeto 'modelo'"
        return find("UPPER(modelo.modelo) = UPPER(?1)", modelo).firstResult();
    }
}