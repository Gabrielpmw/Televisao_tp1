package br.unitins.tp1.model;

import br.unitins.tp1.model.Televisao.Televisao;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Fornecedor extends DefaultEntity{

    @Column
    private String nome;

    @ManyToMany
    @JoinTable(
            name = "fornecedor_televisao",
            joinColumns = @JoinColumn(name = "fornecedor_id"),
            inverseJoinColumns = @JoinColumn(name = "televisao_id")
    )
    private List<Televisao> televisaos = new ArrayList<>();

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL)
    private List<Telefone> telefones = new ArrayList<>();

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Televisao> getTelevisaos() {
        return televisaos;
    }

    public void setTelevisaos(List<Televisao> televisaos) {
        this.televisaos = televisaos;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }
}
