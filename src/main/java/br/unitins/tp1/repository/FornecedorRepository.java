package br.unitins.tp1.repository;

import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.PessoaJuridica.Fornecedor;
import br.unitins.tp1.model.Televisao.Televisao;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class FornecedorRepository implements PanacheRepository<Fornecedor> {

    public List<Marca> findMarcasByFornecedor(Long fornecedorId) {
        Fornecedor fornecedor = find("FROM Fornecedor f LEFT JOIN FETCH f.marcas WHERE f.id = ?1", fornecedorId)
                .firstResultOptional()
                .orElse(null);

        if (fornecedor == null) {
            return new ArrayList<>();
        }

        return fornecedor.getMarcas();
    }

    // Correto
    public PanacheQuery<Fornecedor> findByNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return findAll();
        }

        // Altere 'nome' para 'razaoSocial' aqui
        return find("UPPER(razaoSocial) LIKE ?1", "%" + nome.toUpperCase() + "%");
    }

//    public Fornecedor findFornecedorByTelefone(Long idTelefone) {
//        return find("SELECT f FROM Fornecedor f JOIN f.telefones t WHERE t.id = ?1", idTelefone)
//                .firstResult();
//    }
}
