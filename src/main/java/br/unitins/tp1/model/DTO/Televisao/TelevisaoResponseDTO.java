package br.unitins.tp1.model.DTO.Televisao;
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
        Integer estoque,
        long idModelo,
        long idMarca,
        String nomeImagem,
        Boolean ativo
) {
    public static TelevisaoResponseDTO valueOf(Televisao tv) {
        if (tv == null) return null;


        return new TelevisaoResponseDTO(
                tv.getId(),
                tv.getModelo().getMarca().getNomeMarca(),
                tv.getModelo().getModelo(),
                tv.getDescricao(),
                tv.getResolucao(),
                tv.getTipoTela(),
                DimensaoResponseDTO.valueOf(tv.getDimensao()),
                tv.getValor(),
                tv.getEstoque(),

                tv.getModelo().getId(),
                tv.getModelo().getMarca().getId(),
                tv.getNomeImagem(),
                tv.getAtivo()
        );
    }
}