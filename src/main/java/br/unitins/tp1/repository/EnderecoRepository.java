package br.unitins.tp1.repository;

import br.unitins.tp1.model.Endereco.Endereco;
import br.unitins.tp1.model.Endereco.Municipio;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    public List<Endereco> findByCep(String cep) {
        return list("cep", cep);
    }

    public List<Endereco> findByUsername(String username) {
        return find("usuario.username", username).list();
    }

    public List<Endereco> findByUsuarioId(Long idUsuario) {
        return list("usuario.id", idUsuario);
    }
}
