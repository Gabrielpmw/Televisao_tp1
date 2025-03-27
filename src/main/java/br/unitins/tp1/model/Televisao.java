package br.unitins.tp1.model;

import jakarta.persistence.*;

@Entity
public class Televisao extends DefaultEntity{

    @Column(length = 60, nullable = false)
    private String marca;

    @Column(length = 60, nullable = false)
    private String modelo;

    @Column(length = 60, nullable = false)
    private int polegada;

    @Column(length = 60, nullable = false)
    private String resolucao;

    @Enumerated(EnumType.STRING)
    @Column(length = 60, nullable = false)
    private TipoTela tipoTela;

    @ManyToOne
    @JoinColumn(name = "id_fabricante")
    private Fabricante fabricante;

    public Fabricante getFabricante() {
        return fabricante;
    }

    public void setFabricante(Fabricante fabricante) {
        this.fabricante = fabricante;
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

    public int getPolegada() {
        return polegada;
    }

    public void setPolegada(int polegada) {
        this.polegada = polegada;
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
}
