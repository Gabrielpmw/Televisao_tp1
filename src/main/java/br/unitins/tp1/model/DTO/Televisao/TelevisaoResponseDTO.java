package br.unitins.tp1.model.DTO.Televisao;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.model.Televisao;
import br.unitins.tp1.model.TipoTela;

public record TelevisaoResponseDTO(
        long id,
        String marca,
        String modelo,
        int polegada,
        String resolucao,
        TipoTela tipoTela,
        FabricanteResponseDTO fabricanteResponseDTO
) {
    public static TelevisaoResponseDTO valueOf(Televisao tv){
        if (tv == null) return null;

        return new TelevisaoResponseDTO(tv.getId(), tv.getMarca(), tv.getModelo(), tv.getPolegada(),
                tv.getResolucao(), tv.getTipoTela(), FabricanteResponseDTO.valueOf(tv.getFabricante()));
    }
}
