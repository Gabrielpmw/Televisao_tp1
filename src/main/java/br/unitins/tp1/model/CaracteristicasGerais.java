package br.unitins.tp1.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class CaracteristicasGerais extends DefaultEntity{

    @Column
    private String sistemaOperacioanl;

    @Column
    private int quantidadeHDMI;

    @Column
    private int quantidadeUSB;

    @Column
    private boolean smartTV;

    public String getSistemaOperacioanl() {
        return sistemaOperacioanl;
    }

    public void setSistemaOperacioanl(String sistemaOperacioanl) {
        this.sistemaOperacioanl = sistemaOperacioanl;
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
}
