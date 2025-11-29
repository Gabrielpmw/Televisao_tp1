package br.unitins.tp1.model.DTO.Endereco.Estado;

import br.unitins.tp1.model.Endereco.Estado;


public record EstadoResponseDTO(
        long idEstado,
        String nome,
        String sigla,
        String regiao
) {

    public static EstadoResponseDTO valueOf(Estado estado){
        if (estado == null) return null;

        return new EstadoResponseDTO(estado.getId(), estado.getNome(), estado.getSigla(), estado.getRegiao().getNOME());
    }
}
