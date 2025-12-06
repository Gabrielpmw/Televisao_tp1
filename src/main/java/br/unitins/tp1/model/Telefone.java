package br.unitins.tp1.model;

import br.unitins.tp1.model.PessoaJuridica.Fabricante;
import br.unitins.tp1.model.PessoaJuridica.Fornecedor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Telefone extends DefaultEntity{

    @Column(length = 2)
    String ddd;

    @Column(length = 9, unique = true)
    String numero;

    @ManyToOne
    @JoinColumn(name = "fabricante_id")
    private Fabricante fabricante;

    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    private Fornecedor fornecedor;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
