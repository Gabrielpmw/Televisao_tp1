package br.unitins.tp1.repository;

import br.unitins.tp1.model.Fornecedor;
import br.unitins.tp1.model.Televisao.Televisao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class FornecedorRepository implements PanacheRepository<Fornecedor> {

    public List<Televisao> findTelevisaoByFornecedor(long idFornecedor){
        return find("SELECT t FROM Televisao t JOIN t.fornecedores f WHERE f.id = ?1", idFornecedor)
                .project(Televisao.class)
                .list();
    }

    public Fornecedor findFornecedorByTelefone(Long idTelefone) {
        return find("SELECT f FROM Fornecedor f JOIN f.telefones t WHERE t.id = ?1", idTelefone)
                .firstResult();
    }
}
