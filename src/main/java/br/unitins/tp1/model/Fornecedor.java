package br.unitins.tp1.model;

import br.unitins.tp1.model.Televisao.Televisao;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Fornecedor extends DefaultEntity{

    @Column(length = 60)
    private String nome;

    @Column(length = 14, unique = true)
    private String cnpj;

    @ManyToMany
    @JoinTable(
            name = "fornecedor_televisao",
            joinColumns = @JoinColumn(name = "fornecedor_id"),
            inverseJoinColumns = @JoinColumn(name = "televisao_id")
    )
    private List<Televisao> televisaos = new ArrayList<>();

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
