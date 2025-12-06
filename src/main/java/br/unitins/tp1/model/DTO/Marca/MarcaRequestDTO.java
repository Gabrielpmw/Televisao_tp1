package br.unitins.tp1.model.DTO.Marca;

public record MarcaRequestDTO(
        String nomeMarca,
        String descricao,
        long idFabricante
) {

}
