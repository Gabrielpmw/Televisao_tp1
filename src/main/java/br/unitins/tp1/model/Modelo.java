package br.unitins.tp1.model;

import br.unitins.tp1.model.Televisao.Televisao;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Modelo extends DefaultEntity{

    @Column
    private String modelo;

    @Column
    private int mesesGarantia;

    @Column
    private LocalDate anoLancamento;

    @Column(nullable = false)
    private Boolean ativo = true;

    @ManyToOne
    @JoinColumn(name = "caracteristicas_id")
    private CaracteristicasGerais caracteristicas;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @OneToMany(mappedBy = "modelo")
    @JsonIgnore
    private List<Televisao> televisoes = new ArrayList<>();

    public CaracteristicasGerais getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(CaracteristicasGerais caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getMesesGarantia() {
        return mesesGarantia;
    }

    public void setMesesGarantia(int mesesGarantia) {
        this.mesesGarantia = mesesGarantia;
    }

    public LocalDate getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(LocalDate anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public List<Televisao> getTelevisoes() {
        return televisoes;
    }

    public void setTelevisoes(List<Televisao> televisoes) {
        this.televisoes = televisoes;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
