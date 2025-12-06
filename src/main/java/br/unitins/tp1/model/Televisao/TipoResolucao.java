package br.unitins.tp1.model.Televisao;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum TipoResolucao {
    HD(1, "HD", "1280 x 720"),
    FULL_HD(2, "Full HD", "1920 x 1080"),
    UHD_4K(3, "4K", "3840 x 2160"),
    UHD_8K(4, "8K", "7680 x 4320");

    private final int ID;
    private final String NOME;
    private final String PIXELS;

    TipoResolucao(int id, String nome, String pixel) {
        this.ID = id;
        this.NOME = nome;
        this.PIXELS = pixel;
    }

    public static TipoResolucao valueOf(Integer id) {
        if (id == null) return null;

        for (TipoResolucao resolucao : TipoResolucao.values()) {
            if (resolucao.getID() == id) {
                return resolucao;
            }
        }
        return null;
    }

    ;

    public int getID() {
        return ID;
    }

    public String getNOME() {
        return NOME;
    }

    public String getPIXELS() {
        return PIXELS;
    }
}
