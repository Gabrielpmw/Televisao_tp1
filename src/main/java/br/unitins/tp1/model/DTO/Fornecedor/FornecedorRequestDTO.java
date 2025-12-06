package br.unitins.tp1.model.DTO.Fornecedor;

import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
import br.unitins.tp1.model.Telefone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record FornecedorRequestDTO(
        @NotBlank(message = "CNPJ deve ser informado")
        String razaoSocial,

        @NotBlank(message = "CNPJ deve ser informado")
        @Size(max = 14, message = "Máximo de 14 caracteres")
        String cnpj,

        boolean status,

        String email,

        @NotBlank(message = "O id televisao deve ser informado")
        @Positive(message = "Deve informar apenas valores positivos")
        List<TelefoneRequestDTO> telefones
) {
}
