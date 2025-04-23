package br.unitins.tp1.model.Televisao;

import br.unitins.tp1.model.DefaultEntity;
import br.unitins.tp1.model.Fabricante;
import br.unitins.tp1.model.Fornecedor;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Televisao extends DefaultEntity {

    @Column(length = 60, nullable = false)
    private String marca;

    @Column(length = 60, nullable = false)
    private String modelo;

    @Column(length = 60, nullable = false)
    private String resolucao;

    @Enumerated(EnumType.STRING)
    @Column(length = 60, nullable = false)
    private TipoTela tipoTela;


    @ManyToOne
    @JoinColumn(name = "id_fabricante")
    private Fabricante fabricante;

    @ManyToOne
    @JoinColumn(name = "id_dimensao")
    private Dimensao dimensao;

    @ManyToMany(mappedBy = "televisaos")
    private List<Fornecedor> fornecedores = new ArrayList<>();
    

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
    }

    public List<Fornecedor> getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(List<Fornecedor> fornecedores) {
        this.fornecedores = fornecedores;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getResolucao() {
        return resolucao;
    }

    public void setResolucao(String resolucao) {
        this.resolucao = resolucao;
    }

    public TipoTela getTipoTela() {
        return tipoTela;
    }

    public void setTipoTela(TipoTela tipoTela) {
        this.tipoTela = tipoTela;
    }

    public Dimensao getDimensao() {
        return dimensao;
    }

    public void setDimensao(Dimensao dimensao) {
        this.dimensao = dimensao;
    }
}
