package br.unitins.tp1.repository;

import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.PessoaJuridica.Fabricante;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FabricanteRepository implements PanacheRepository<Fabricante> {

    public PanacheQuery<Fabricante> findByNome(String razaoSocial) {

        if (razaoSocial == null){
            return null;
        }

        // Adicionei o "AND ativo = true" para filtrar os desativados
        return find("UPPER(razaoSocial) LIKE ?1 AND ativo = true", razaoSocial.toUpperCase() + "%");
    }

    // 2. Busca marcas por fabricante (Ajustada para verificar se o fabricante está ativo)
    public List<Marca> findMarcasByFabricante(Long idFabricante) {

        // Adicionei "AND f.ativo = true".
        // Se o fabricante existir mas estiver "deletado", essa busca retorna vazio.
        Fabricante fabricante = find("FROM Fabricante f LEFT JOIN FETCH f.marcas WHERE f.id = ?1 AND f.ativo = true", idFabricante)
                .firstResultOptional()
                .orElse(null);

        if (fabricante == null) {
            return new ArrayList<>();
        }

        return fabricante.getMarcas();
    }

    // 3. NOVA QUERY: Lista apenas os desativados (A "Lixeira")
    public List<Fabricante> findInativos() {
        // Busca simples onde o campo ativo é false
        return list("ativo", false);
    }
}