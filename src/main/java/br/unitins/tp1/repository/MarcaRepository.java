package br.unitins.tp1.repository;

import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.Modelo;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MarcaRepository implements PanacheRepository<Marca> {

    public List<Modelo> findModelosByMarca(Long idMarca) {
        Marca marca = find("FROM Marca m LEFT JOIN FETCH m.modelos WHERE m.id = ?1", idMarca)
                .firstResultOptional()
                .orElse(null);

        if (marca == null) {
            return new ArrayList<>();
        }

        return marca.getModelos();
    }

    public PanacheQuery<Marca> findByNome(String nomeMarca) {
        if (nomeMarca == null || nomeMarca.isBlank()) {
            return find("1 = 1"); // retorna todas as marcas
        }

        return find("UPPER(nomeMarca) LIKE ?1", "%" + nomeMarca.toUpperCase() + "%");
    }
}
