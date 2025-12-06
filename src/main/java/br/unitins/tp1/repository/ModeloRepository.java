package br.unitins.tp1.repository;

import br.unitins.tp1.model.Modelo;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List; // Importar java.util.List

@ApplicationScoped
public class ModeloRepository implements PanacheRepository<Modelo> {

    public PanacheQuery<Modelo> findByNome(String nomeModelo) {
        if (nomeModelo == null || nomeModelo.isBlank()) {
            // Retorna todos os modelos QUE ESTÃO ATIVOS
            return find("ativo = true");
        }

        // Busca pelo nome E garante que está ativo
        return find("UPPER(modelo) LIKE ?1 AND ativo = true", nomeModelo.toUpperCase() + "%");
    }

    // --- BUSCA POR MARCA (Apenas Ativos) ---
    public List<Modelo> findByMarca(Long idMarca) {
        if (idMarca == null) {
            return List.of();
        }
        // Filtra pela marca E pelo status ativo
        return find("marca.id = ?1 AND ativo = true", idMarca).list();
    }

    // --- BUSCA DA LIXEIRA (Apenas Inativos) ---
    // Retorno PanacheQuery para permitir paginação no Service (ex: .page(0, 10).list())
    public PanacheQuery<Modelo> findInativos() {
        return find("ativo = false");
    }
}