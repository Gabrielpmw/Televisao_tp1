package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class CaracteristicasGerais extends DefaultEntity{

    @Column
    private String nome;

    @Column
    private String sistemaOperacional;

    @Column
    private int quantidadeHDMI;

    @Column
    private int quantidadeUSB;

    @Column
    private boolean smartTV;

    @OneToMany(mappedBy = "caracteristicas")
    @JsonIgnore
    private List<Modelo> modelos = new ArrayList<>();

    public List<Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(List<Modelo> modelos) {
        this.modelos = modelos;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public int getQuantidadeHDMI() {
        return quantidadeHDMI;
    }

    public void setQuantidadeHDMI(int quantidadeHDMI) {
        this.quantidadeHDMI = quantidadeHDMI;
    }

    public int getQuantidadeUSB() {
        return quantidadeUSB;
    }

    public void setQuantidadeUSB(int quantidadeUSB) {
        this.quantidadeUSB = quantidadeUSB;
    }

    public boolean isSmartTV() {
        return smartTV;
    }

    public void setSmartTV(boolean smartTV) {
        this.smartTV = smartTV;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
