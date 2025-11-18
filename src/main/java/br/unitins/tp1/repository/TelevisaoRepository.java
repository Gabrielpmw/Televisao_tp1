package br.unitins.tp1.repository;

import br.unitins.tp1.model.Modelo;
import br.unitins.tp1.model.Televisao.Televisao;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


import java.util.List;

@ApplicationScoped
public class TelevisaoRepository implements PanacheRepository<Televisao> {

//    public PanacheQuery<Televisao> findByIds(List<Long> id) {
//        return find("SELECT t from Televisao t WHERE t.id in ?1", id);
//    }

    public Modelo findModeloByTelevisao(Long televisaoId) {
        Televisao tv = findByIdOptional(televisaoId)
                .orElse(null);

        if (tv != null) {
            return tv.getModelo();
        }
        return null;
    }

    public PanacheQuery<Televisao> findByNomeModelo(String nomeModelo) {

        String query = "SELECT t FROM Televisao t WHERE UPPER(t.modelo.modelo) LIKE ?1";
        String parametro = "%" + nomeModelo.toUpperCase() + "%";

        return find(query, parametro);
    }

}