package br.unitins.tp1.model.DTO.Fabricante;

import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record FabricanteRequestDTO(

        @NotBlank(message = "CNPJ deve ser informado")
        String razaoSocial,

        @NotNull(message = "Data de abertura deve ser informada")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate dataAbertura,

        @NotBlank(message = "CNPJ deve ser informado")
        @Size(max = 14, message = "Máximo de 14 caracteres")
        String cnpj,

        @NotBlank(message = "Pais deve ser informado")
        @Size(max = 60, message = "Máximo de 60 caracteres")
        String paisSede,

        boolean ativo,

        @NotBlank(message = "O id televisao deve ser informado")
        @Positive(message = "Deve informar apenas valores positivos")
        List<TelefoneRequestDTO> telefones
){

}
