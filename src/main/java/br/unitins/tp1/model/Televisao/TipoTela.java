package br.unitins.tp1.model.Televisao;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoTela {
    LED(1, "Led"),
    OLED(2, "Oled"),
    QLED(3, "Qled"),
    LCD(4, "LCD"),
    PLASMA(5, "Plasma");

    private final int ID;
    private final String NOME;

    TipoTela(int ID, String NOME) {
        this.ID = ID;
        this.NOME = NOME;
    }

    public int getID() {
        return ID;
    }

    public String getNOME() {
        return NOME;
    }

    public static TipoTela valueOf(Integer id){
        if (id == null) return null;

        for (TipoTela tela : TipoTela.values()){
            if (tela.getID() == id){
                return tela;
            }
        }
        return null;
    };
}
