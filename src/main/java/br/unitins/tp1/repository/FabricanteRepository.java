package br.unitins.tp1.repository;

import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.PessoaJuridica.Fabricante;
import br.unitins.tp1.model.Televisao.Televisao;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import net.bytebuddy.asm.Advice;
import org.jboss.logging.annotations.Param;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FabricanteRepository implements PanacheRepository<Fabricante> {

    public PanacheQuery<Fabricante> findByNome(String razaoSocial) {

        if (razaoSocial == null){
            return null;
        }

        return find("UPPER(razaoSocial) LIKE ?1", "%" + razaoSocial.toUpperCase() + "%");
    }
    public List<Marca> findMarcasByFabricante(Long idFabricante) {
        Fabricante fabricante = find("FROM Fabricante f LEFT JOIN FETCH f.marcas WHERE f.id = ?1", idFabricante)
                .firstResultOptional()
                .orElse(null);

        if (fabricante == null) {
            return new ArrayList<>();
        }

        return fabricante.getMarcas();
    }
}
