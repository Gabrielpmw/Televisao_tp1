package br.unitins.tp1.model.DTO.Endereco.Municipio;

import br.unitins.tp1.model.DTO.Endereco.Estado.EstadoResponseDTO;
import br.unitins.tp1.model.Endereco.Municipio;

public record MunicipioResponseDTO(
        long idMunicipio,
        String municipio,
        EstadoResponseDTO estado
) {

    public static MunicipioResponseDTO valueOf(Municipio municipio){
        if (municipio == null) return null;

        return new MunicipioResponseDTO(municipio.getId(), municipio.getNome(),
                                        EstadoResponseDTO.valueOf(municipio.getEstado()));
    }
}
