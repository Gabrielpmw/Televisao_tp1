package br.unitins.tp1.model.DTO.Televisao;

import br.unitins.tp1.model.DTO.Modelo.ModeloResponseDTO;
import br.unitins.tp1.model.Modelo;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.model.Televisao.TipoResolucao;
import br.unitins.tp1.model.Televisao.TipoTela;


public record TelevisaoResponseDTO(
        long idTelevisao,
        String marca,
        String modelo,
        String descricao,
        TipoResolucao resolucao,
        TipoTela tipoTela,
        DimensaoResponseDTO dimensao,
        Double valor,
        Integer estoque
) {
    public static TelevisaoResponseDTO valueOf(Televisao tv) {
        if (tv == null) return null;

        ModeloResponseDTO modeloTeste = ModeloResponseDTO.valueOf(tv.getModelo());

        return new TelevisaoResponseDTO(
                tv.getId(),
                tv.getModelo().getMarca().getNomeMarca(),
                tv.getModelo().getModelo(),
                tv.getDescricao(),
                tv.getResolucao(),
                tv.getTipoTela(),
                DimensaoResponseDTO.valueOf(tv.getDimensao()),
                tv.getValor(),
                tv.getEstoque());
    }
}
