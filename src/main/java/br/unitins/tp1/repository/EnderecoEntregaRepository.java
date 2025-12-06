package br.unitins.tp1.repository;

import br.unitins.tp1.model.Endereco.EnderecoEntrega;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EnderecoEntregaRepository implements PanacheRepository<EnderecoEntrega> {
}
