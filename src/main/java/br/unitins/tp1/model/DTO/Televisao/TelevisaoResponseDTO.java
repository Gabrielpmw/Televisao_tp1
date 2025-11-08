package br.unitins.tp1.model.DTO.Televisao;

import br.unitins.tp1.model.DTO.Modelo.ModeloResponseDTO;
import br.unitins.tp1.model.Modelo;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.model.Televisao.TipoResolucao;
import br.unitins.tp1.model.Televisao.TipoTela;


public record TelevisaoResponseDTO(
        long idTelevisao,
        String marca,       // Continua ótimo para exibição
        String modelo,      // Continua ótimo para exibição
        String descricao,
        TipoResolucao resolucao,
        TipoTela tipoTela,
        DimensaoResponseDTO dimensao,
        Double valor,
        Integer estoque,

        // --- ADIÇÕES NECESSÁRIAS PARA O FORMULÁRIO DE EDIÇÃO ---
        long idModelo,
        long idMarca
        // --- FIM DAS ADIÇÕES ---
) {
    public static TelevisaoResponseDTO valueOf(Televisao tv) {
        if (tv == null) return null;

        // Removi a variável não utilizada 'modeloTeste'

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

                // --- ADIÇÕES NECESSÁRIAS PARA O FORMULÁRIO DE EDIÇÃO ---
                tv.getModelo().getId(), // Pega o ID do modelo
                tv.getModelo().getMarca().getId() // Pega o ID da marca
                // --- FIM DAS ADIÇÕES ---
        );
    }
}