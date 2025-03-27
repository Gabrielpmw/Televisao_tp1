package br.unitins.tp1.model.DTO.Fabricante;

import br.unitins.tp1.model.Fabricante;

public record FabricanteResponseDTO(
        long id,
        String nome,
        String cnpj,
        String paisSede,
        String telefone
) {
    public static FabricanteResponseDTO valueOf(Fabricante fabricante){
        if (fabricante == null) return null;

        return new FabricanteResponseDTO(fabricante.getId(), fabricante.getNome(), fabricante.getCnpj(),
                fabricante.getPaisSede(), fabricante.getTelefone());
    }
}
