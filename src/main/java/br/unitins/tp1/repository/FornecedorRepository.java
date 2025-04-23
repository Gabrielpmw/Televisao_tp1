package br.unitins.tp1.repository;

import br.unitins.tp1.model.Fornecedor;
import br.unitins.tp1.model.Televisao.Televisao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class FornecedorRepository implements PanacheRepository<Fornecedor> {

    public List<Televisao> findTelevisoesByFornecedor(long id) {
        Fornecedor fornecedor = findById(id);
        return fornecedor != null ? fornecedor.getTelevisaos() : List.of();
    }

}
