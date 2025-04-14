package br.unitins.tp1.model.DTO.Fabricante;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record FabricanteRequestDTO(
        @NotBlank(message = "Nome deve ser informado")
        @Size(max = 60, message = "Máximo de 60 caracteres")
        String nome,

        @NotBlank(message = "CNPJ deve ser informado")
        @Size(max = 14, message = "Máximo de 14 caracteres")
        String cnpj,

        @NotBlank(message = "Pais deve ser informado")
        @Size(max = 60, message = "Máximo de 60 caracteres")
        String paisSede,

        List<Long> idTelefone
){

}
