package br.unitins.tp1.model.DTO.Televisao;

import br.unitins.tp1.model.Televisao.Dimensao;

public record DimensaoResponseDTO(
        long id,
        int comprimento,
        int altura,
        int polegada
) {
    public static DimensaoResponseDTO valueOf(Dimensao dimensao){
        if (dimensao == null) return null;

        return new DimensaoResponseDTO(dimensao.getId() ,dimensao.getComprimento(), dimensao.getAltura(), dimensao.getPolegada());
    }
}
