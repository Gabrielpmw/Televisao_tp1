package br.unitins.tp1.repository;

import br.unitins.tp1.model.Funcionario;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class FuncionarioRepository implements PanacheRepository<Funcionario> {
    public PanacheQuery<Funcionario> findByNome(String nome) {
        if (nome == null) {
            return null;
        }

        // RETORNA PanacheQuery PARA PERMITIR PAGINAÇÃO NO SERVICE
        // O .list() foi removido aqui. Quem chamar este método deve usar .page().list() ou apenas .list()
        return find("SELECT c FROM Funcionario c WHERE UPPER(c.nome) LIKE ?1", nome.toUpperCase() + "%");
    }

    public Funcionario findByCpf(String cpf) {
        return find("SELECT c FROM Funcionario c WHERE c.cpf = ?1",  cpf ).firstResult();
    }

    public Funcionario findByUsername(String username) {
        return find("SELECT c FROM Funcionario c WHERE c.usuario.username = ?1", username).firstResult();
    }
}
