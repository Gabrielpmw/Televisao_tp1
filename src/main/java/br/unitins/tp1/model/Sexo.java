package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Sexo {
    MASCULINO(1, "masculino"),
    FEMININO(2, "feminino");

    private final int ID;
    private final String NOME;

    Sexo(int ID, String NOME) {
        this.ID = ID;
        this.NOME = NOME;
    }

    public static Sexo valueOf(Integer id){
        if (id == null) return null;

        for (Sexo sexo : Sexo.values()){
            if (sexo.getID() == id){
                return sexo;
            }
        }
        return null;
    };

    public int getID() {
        return ID;
    }

    public String getNOME() {
        return NOME;
    }
}
