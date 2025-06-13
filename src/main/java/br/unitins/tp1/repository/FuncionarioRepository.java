package br.unitins.tp1.repository;

import br.unitins.tp1.model.Funcionario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class FuncionarioRepository implements PanacheRepository<Funcionario> {
    public List<Funcionario> findByNome(String nome) {
        return find("SELECT c FROM Funcionario c WHERE c.nome LIKE ?1", "%" + nome + "%").list();
    }
    public Funcionario findByCpf(String cpf) {
        return find("SELECT c FROM Funcionario c WHERE c.cpf = ?1",  cpf ).firstResult();
    }

    public Funcionario findByUsername(String username) {
        return find("SELECT c FROM Funcionario c WHERE c.usuario.username = ?1", username).firstResult();    }
}
