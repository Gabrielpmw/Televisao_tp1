package br.unitins.tp1.model.DTO;

public class TelevisaoDTO {
    private final String marca;
    private final String modelo;
    private final String resolucao;
    private final int polegada;
    private final int idTipoTela;

    public TelevisaoDTO(String marca, String modelo, String resolucao, int polegada, int idTipoTela) {
        this.marca = marca;
        this.modelo = modelo;
        this.resolucao = resolucao;
        this.polegada = polegada;
        this.idTipoTela = idTipoTela;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getResolucao() {
        return resolucao;
    }

    public int getPolegada() {
        return polegada;
    }

    public int getIdTipoTela() {
        return idTipoTela;
    }
}
