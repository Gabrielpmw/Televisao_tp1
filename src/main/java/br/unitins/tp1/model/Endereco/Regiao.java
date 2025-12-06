package br.unitins.tp1.model.Endereco;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)

public enum Regiao {
    NORTE(1, "norte"),
    NORDESTE(2, "nordeste"),
    CENTRO_OESTE(3, "centro-oeste"),
    SUDESTE(4, "sudeste"),
    SUL(5, "sul");

    private final Integer ID;
    private final String NOME;

    Regiao(int ID, String NOME) {
        this.ID = ID;
        this.NOME = NOME;
    }

    public Integer getID() {
        return ID;
    }

    public String getNOME() {
        return NOME;
    }

    public static Regiao valueOf(Integer id){
        if (id == null) return null;

        for (Regiao regiao : Regiao.values()){
            if (regiao.getID().equals(id)){
                return regiao;
            }
        }
        return null;
    };
}
