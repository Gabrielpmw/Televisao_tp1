package br.unitins.tp1.model.Pedido;

import br.unitins.tp1.model.DefaultEntity;
import br.unitins.tp1.model.Endereco.EnderecoEntrega;
import br.unitins.tp1.model.Pagamento.Pagamento;
import br.unitins.tp1.model.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido extends DefaultEntity {

    @Column
    private LocalDateTime dataPedido;

    @Column
    private double valorTotal;

    // 💡 CAMPO ADICIONADO: Valor do frete
    @Column
    private double valorFrete; // Nome alterado para valorFrete (opcional)

    @Column
    @Enumerated(EnumType.STRING)
    private StatusPedido statusPedido;

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private EnderecoEntrega enderecoEntrega;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itensPedidos = new ArrayList<>();

    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private Pagamento formaPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDateTime dataPedido) {
        this.dataPedido = dataPedido;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    // 💡 GETTER e SETTER para o novo campo
    public double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public EnderecoEntrega getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public List<ItemPedido> getItensPedidos() {
        return itensPedidos;
    }

    public void setItensPedidos(List<ItemPedido> itensPedidos) {
        this.itensPedidos = itensPedidos;
    }

    public Pagamento getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(Pagamento formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}