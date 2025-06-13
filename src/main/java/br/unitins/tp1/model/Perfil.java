package br.unitins.tp1.model;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Perfil {
    ADM(1, "adm"),
    USER(2, "cliente");

    private final int ID;
    private final String NOME;

    Perfil(int ID, String NOME) {
        this.ID = ID;
        this.NOME = NOME;
    }

    public static Perfil valueOf(Integer id){
        if (id == null) return null;

        for (Perfil perfil : Perfil.values()){
            if (perfil.getID() == id){
                return perfil;
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
