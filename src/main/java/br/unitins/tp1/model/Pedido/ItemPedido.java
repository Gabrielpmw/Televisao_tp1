package br.unitins.tp1.model.Pedido;

import br.unitins.tp1.model.DefaultEntity;
import br.unitins.tp1.model.Televisao.Televisao;
import jakarta.persistence.*;


@Entity
public class ItemPedido extends DefaultEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_televisao")
    private Televisao televisao;

    @Column
    private double preco;

    @Column
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    public Televisao getTelevisao() {
        return televisao;
    }

    public void setTelevisao(Televisao televisao) {
        this.televisao = televisao;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
