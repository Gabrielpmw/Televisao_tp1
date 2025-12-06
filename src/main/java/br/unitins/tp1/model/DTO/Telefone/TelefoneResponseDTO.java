package br.unitins.tp1.model.DTO.Telefone;

import br.unitins.tp1.model.Telefone;

public record TelefoneResponseDTO(
        long id,
        String ddd,
        String numero
) {
    public static TelefoneResponseDTO valueOf(Telefone telefone){
        if (telefone == null) return null;

        return new TelefoneResponseDTO(telefone.getId(), telefone.getDdd(), telefone.getNumero());
    }
}
