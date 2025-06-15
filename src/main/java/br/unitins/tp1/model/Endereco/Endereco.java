package br.unitins.tp1.model.Endereco;

import br.unitins.tp1.model.DefaultEntity;
import br.unitins.tp1.model.Usuario;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Endereco extends DefaultEntity {

    @Column(length = 60)
    private String cep;

    @Column(length = 60)
    private String bairro;

    @Column
    private int numero;

    @Column(length = 60)
    private String complemento;

    @ManyToOne
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;
    //cascade = CascadeType.ALL, orphanRemoval = true
    @OneToMany(mappedBy = "endereco")
    private List<EnderecoEntrega> enderecosEntrega;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<EnderecoEntrega> getEnderecosEntrega() {
        return enderecosEntrega;
    }

    public void setEnderecosEntrega(List<EnderecoEntrega> enderecosEntrega) {
        this.enderecosEntrega = enderecosEntrega;
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
