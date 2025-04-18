package br.unitins.tp1.model.DTO.Televisao;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.model.Televisao.TipoTela;


public record TelevisaoResponseDTO(
        long id,
        String marca,
        String modelo,
        String resolucao,
        TipoTela tipoTela,
        FabricanteResponseDTO fabricante,
        DimensaoResponseDTO dimensao
) {
    public static TelevisaoResponseDTO valueOf(Televisao tv){
        if (tv == null) return null;

        return new TelevisaoResponseDTO(tv.getId(),
                tv.getMarca(),
                tv.getModelo(),
                tv.getResolucao(),
                tv.getTipoTela(),
                FabricanteResponseDTO.valueOf(tv.getFabricante()),
                DimensaoResponseDTO.valueOf(tv.getDimensao()));
    }
}
