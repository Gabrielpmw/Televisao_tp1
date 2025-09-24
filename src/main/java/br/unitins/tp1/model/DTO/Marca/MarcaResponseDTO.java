package br.unitins.tp1.model.DTO.Marca;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.model.Marca;

public record MarcaResponseDTO(
        long id,
        String marca,
        String descricao,
        FabricanteResponseDTO response
){
    public static MarcaResponseDTO valueOf(Marca marca){
        if (marca == null) return null;

        FabricanteResponseDTO fabricante = FabricanteResponseDTO.valueOf(marca.getFabricante());

        return new MarcaResponseDTO(marca.getId(), marca.getNomeMarca(), marca.getDescricao(), fabricante);
    }
}
