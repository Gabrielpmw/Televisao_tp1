package br.unitins.tp1.model.DTO.Endereco.Endereco;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EnderecoEntregaRequestDTO(
        @Positive(message = "Informe um valor positivo")
        @NotNull(message = "O id endereço não pode ser null")
        Long idEndereco
) {
}
