package br.unitins.tp1.model.DTO.Televisao;

import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.model.Televisao.TipoResolucao;
import br.unitins.tp1.model.Televisao.TipoTela;


public record TelevisaoResponseDTO(
        long idTelevisao,
        String marca,
        String modelo,
        TipoResolucao resolucao,
        TipoTela tipoTela,
        DimensaoResponseDTO dimensao,
        Double valor,
        Integer estoque
) {
    public static TelevisaoResponseDTO valueOf(Televisao tv) {
        if (tv == null) return null;

        return new TelevisaoResponseDTO(tv.getId(),
                tv.getMarca(),
                tv.getModelo(),
                tv.getResolucao(),
                tv.getTipoTela(),
                DimensaoResponseDTO.valueOf(tv.getDimensao()),
                tv.getValor(),
                tv.getEstoque());
    }
}
