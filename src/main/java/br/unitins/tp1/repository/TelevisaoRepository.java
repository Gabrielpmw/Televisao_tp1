package br.unitins.tp1.repository;

import br.unitins.tp1.model.Modelo;
import br.unitins.tp1.model.Televisao.Televisao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


import java.util.List;

@ApplicationScoped
public class TelevisaoRepository implements PanacheRepository<Televisao> {

    public List<Televisao> findByIds(List<Long> id) {
        return find("SELECT t from Televisao t WHERE t.id in ?1", id).stream().toList();
    }

    public Modelo findModeloByTelevisao(Long televisaoId) {
        Televisao tv = findByIdOptional(televisaoId)
                .orElse(null);

        if (tv != null) {
            return tv.getModelo();
        }
        return null;
    }
}