package br.unitins.tp1.model.PessoaJuridica;

import br.unitins.tp1.model.DefaultEntity;
import br.unitins.tp1.model.Telefone;
import br.unitins.tp1.model.Televisao.Televisao;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Fornecedor extends PessoaJuridica {

    @Column(length = 60)
    private String email;

    @ManyToMany
    @JoinTable(
            name = "fornecedor_televisao",
            joinColumns = @JoinColumn(name = "fornecedor_id"),
            inverseJoinColumns = @JoinColumn(name = "televisao_id")
    )
    private List<Televisao> televisaos = new ArrayList<>();

    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefone> telefones = new ArrayList<>();

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
