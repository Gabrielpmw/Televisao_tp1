package br.unitins.tp1.repository;

import br.unitins.tp1.model.PessoaJuridica.Fabricante;
import br.unitins.tp1.model.Televisao.Televisao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class FabricanteRepository implements PanacheRepository<Fabricante> {

    public List<Televisao> findTelevisaoByFabricante(long idFabricante){
        return find("SELECT t FROM Televisao t WHERE t.fabricante.id = ?1", idFabricante)
                .project(Televisao.class)
                .list();
    }

    public Fabricante findByNome(String nome) {
        return find("nome", nome).firstResult();
    }
}
