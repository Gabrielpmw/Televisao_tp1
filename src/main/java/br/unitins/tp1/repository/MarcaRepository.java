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
        // Adicionei "AND m.ativo = true" na query HQL
        Marca marca = find("FROM Marca m LEFT JOIN FETCH m.modelos WHERE m.id = ?1 AND m.ativo = true", idMarca)
                .firstResultOptional()
                .orElse(null);

        if (marca == null) {
            return new ArrayList<>();
        }

        return marca.getModelos();
    }

    // Busca por nome (apenas ATIVOS)
    public PanacheQuery<Marca> findByNome(String nomeMarca) {
        if (nomeMarca == null || nomeMarca.isBlank()) {
            // Se não passar nome, traz todos os ativos
            return find("ativo", true);
        }

        // Filtra por nome (case insensitive) E ativo = true
        return find("UPPER(nomeMarca) LIKE ?1 AND ativo = true", nomeMarca.toUpperCase() + "%");
    }

    // --- NOVA QUERY: Buscar apenas os INATIVOS (Lixeira) ---
    // Você pode usar este método no Service para a listagem da lixeira
    // Se quiser filtrar inativos por nome também, pode adaptar
    public PanacheQuery<Marca> findInativos() {
        return find("ativo", false);
    }
}
