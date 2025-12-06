package br.unitins.tp1.model.Pagamento;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Boleto extends Pagamento{

    @Column
    private String codigoBarras;

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }
}
