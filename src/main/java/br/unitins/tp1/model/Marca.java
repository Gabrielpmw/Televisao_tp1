package br.unitins.tp1.model;

import br.unitins.tp1.model.PessoaJuridica.Fabricante;
import br.unitins.tp1.model.PessoaJuridica.Fornecedor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Marca extends DefaultEntity{

    @Column
    private String nomeMarca;

    @Column
    private String descricao;

    @Column
    private Boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "id_fabricante", nullable = false)
    private Fabricante fabricante;

    @ManyToMany(mappedBy = "marcas")
    private List<Fornecedor> fornecedores = new ArrayList<>();

    @OneToMany(mappedBy = "marca")
    private List<Modelo> modelos = new ArrayList<>();

    public String getNomeMarca() {
        return nomeMarca;
    }

    public void setNomeMarca(String nomeMarca) {
        this.nomeMarca = nomeMarca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public List<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(List<Modelo> modelos) {
        this.modelos = modelos;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }
}
