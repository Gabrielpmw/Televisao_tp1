package br.unitins.tp1.model.DTO.Endereco.Municipio;

import jakarta.validation.constraints.NotBlank;

public record MunicipioCheckDTO(
        @NotBlank
        String nomeCidade,
        @NotBlank
        String uf
) {
}
