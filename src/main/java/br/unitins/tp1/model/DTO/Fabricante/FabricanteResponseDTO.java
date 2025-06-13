package br.unitins.tp1.model.DTO.Fabricante;

import br.unitins.tp1.model.DTO.Telefone.TelefoneResponseDTO;
import br.unitins.tp1.model.Fabricante;

import java.util.List;

public record FabricanteResponseDTO(
        long id,
        String nome,
        String cnpj,
        String paisSede,
        List<TelefoneResponseDTO> telefones
) {
    public static FabricanteResponseDTO valueOf(Fabricante fabricante){
        if (fabricante == null) return null;

        return new FabricanteResponseDTO(fabricante.getId(),
                fabricante.getNome(),
                fabricante.getCnpj(),
                fabricante.getPaisSede(),
                fabricante.getTelefones().stream().map(TelefoneResponseDTO::valueOf).toList());
    }
}
