package br.unitins.tp1.model.Pagamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Cartao extends Pagamento{

    @Column
    private String titular;

    @Column
    private String numero;

    @Column
    private LocalDate dataValidade;

    @Column
    private String cvv;

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
// Nenhuma chave extra aqui!