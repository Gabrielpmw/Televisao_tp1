package br.unitins.tp1.model;


import jakarta.persistence.*;

@Entity
public class Funcionario extends DefaultEntity{

    @OneToOne(mappedBy = "funcionario", cascade = CascadeType.REMOVE)
    private Usuario usuario;

    @Column
    private String nome;

    @Column(unique = true)
    private String cpf;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
