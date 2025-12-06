package br.unitins.tp1.model.PessoaJuridica;

import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.Telefone;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Fabricante extends PessoaJuridica {

    @Column
    private LocalDate anoFundacao;

    @Column
    private String paisSede;

    @Column
    private Boolean ativo = true;

    @OneToMany(mappedBy = "fabricante" ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefone> telefones = new ArrayList<>();

    @OneToMany(mappedBy = "fabricante")
    private List<Marca> marcas;

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public LocalDate getAnoFundacao() {
        return anoFundacao;
    }

    public void setAnoFundacao(LocalDate anoFundacao) {
        this.anoFundacao = anoFundacao;
    }

    public String getPaisSede() {
        return paisSede;
    }

    public void setPaisSede(String paisSede) {
        this.paisSede = paisSede;
    }

    public List<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
