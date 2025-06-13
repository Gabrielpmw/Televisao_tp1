package br.unitins.tp1.model.DTO.Fornecedor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record FornecedorRequestDTO(
        @NotBlank(message = "Nome do fornecedor deve ser informado")
        @Size(max = 60, message = "Máximo 60 caracteres")
        String nome,
        @NotBlank(message = "CNPJ do fornecedor deve ser informado")
        @Size(max = 11, message = "Máximo 11 caracteres")
        String cnpj,

        List<Long> idTelefone,
        List<Long> idTelevisao
) {
}
