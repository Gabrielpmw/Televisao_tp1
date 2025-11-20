package br.unitins.tp1.repository;

import br.unitins.tp1.model.Usuario;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario> {
    public Usuario findByUsernameAndSenha(String username, String senha) {
        return find("username = ?1 AND senha = ?2", username, senha).firstResult();    }

    public Usuario findByUsername(String username) {
        return find("SELECT u FROM Usuario u WHERE u.username = ?1 ", username).firstResult();
    }

    public Usuario findByUsernameAndCpf(String username, String cpf) {
        return find("username = ?1 and cpf = ?2", username, cpf).firstResult();
    }

    public List<Usuario> findByUsernameLikeIgnoreCase(String username) {
        return find("UPPER(username) LIKE UPPER(?1)", "%" + username + "%").list();
    }
}
