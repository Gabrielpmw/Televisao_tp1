package br.unitins.tp1.model.Pagamento;


import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)

public enum StatusPagamento {
    PAGAMENTO_PENDENTE(1, "pagamento pendente"),
    PAGAMENTO_EFETUADO(2, "pagamento efetuado");

    private int ID;
    private String status;

    StatusPagamento(int ID, String status) {
        this.ID = ID;
        this.status = status;
    }

    public int getID() {
        return ID;
    }

    public String getStatus() {
        return status;
    }

    public static StatusPagamento valueOf(Integer id){
        if (id == null) return null;

        for (StatusPagamento status : StatusPagamento.values()){
            if (status.getID() == id){
                return status;
            }
        }
        return null;
    };
}
