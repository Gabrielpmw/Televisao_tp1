package br.unitins.tp1.model.Televisao;

import br.unitins.tp1.model.DefaultEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Dimensao extends DefaultEntity {

    @Column
    private int comprimento;

    @Column
    private int altura;

    @Column
    private int polegada;

    public int getComprimento() {
        return comprimento;
    }

    public void setComprimento(int comprimento) {
        this.comprimento = comprimento;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPolegada() {
        return polegada;
    }

    public void setPolegada(int polegada) {
        this.polegada = polegada;
    }
}
