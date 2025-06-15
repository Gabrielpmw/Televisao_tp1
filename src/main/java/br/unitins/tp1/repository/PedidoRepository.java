package br.unitins.tp1.repository;

import br.unitins.tp1.model.Pagamento.StatusPagamento;
import br.unitins.tp1.model.Pedido.Pedido;
import br.unitins.tp1.model.Pedido.StatusPedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class PedidoRepository implements PanacheRepository<Pedido> {

    public List<Pedido> findPedidoByUsername(String username) {
        return find("usuario.username", username).list();    }

    public List<Pedido> findPedidoByStatusAndUser(int idUsuario, int num) {
        StatusPedido statusPedido = StatusPedido.valueOf(num);
        return find("usuario.id = ?1 and statusPedido = ?2", idUsuario, statusPedido).list();
    }

    public List<Pedido> findPedidoByStatusPedido(int idUsuario, int status){
        StatusPagamento statusPagamento = StatusPagamento.valueOf(status);

        return find (" formapagamento.statusPagamento = ?1 and usuario.id ?= 2", statusPagamento, idUsuario).list();
    }
}
