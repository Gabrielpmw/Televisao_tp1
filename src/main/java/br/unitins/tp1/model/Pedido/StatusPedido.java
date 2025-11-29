package br.unitins.tp1.model.Pedido;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)

public enum StatusPedido {
    PEDIDO_EM_PROCESSO(1, "pedido em processo"),
    SAIU_PARA_ENTREGA(2, "pedido saiu para entrega"),
    ENTREGUE(3, "pedido entregue"),
    CANCELADO(4, "pedido cancelado");

    private int ID;
    private String STATUS;

    StatusPedido(int ID, String STATUS) {
        this.ID = ID;
        this.STATUS = STATUS;
    }

    public static StatusPedido valueOf(Integer id){
        if (id == null) return null;

        for (StatusPedido status : StatusPedido.values()){
            if (status.getID() == id){
                return status;
            }
        }
        return null;
    };

    public int getID() {
        return ID;
    }

    public String getSTATUS() {
        return STATUS;
    }
}
