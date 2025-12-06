package br.unitins.tp1.model.Endereco;

import br.unitins.tp1.model.DefaultEntity;
import br.unitins.tp1.model.Pedido.Pedido;
import jakarta.persistence.*;

@Entity
public class EnderecoEntrega extends DefaultEntity {

    @ManyToOne
    @JoinColumn(name = "id_endereco")
    private Endereco endereco;

    @Column
    private String cep; // Snapshot

    @Column
    private String bairro; // Snapshot

    @Column
    private int numero; // Snapshot

    @Column
    private String complemento; // Snapshot

    // ADICIONADO: Referência ao Município para saber a cidade/estado
    @ManyToOne
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;

    @OneToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
}
