package br.unitins.tp1.model.DTO.Endereco.Endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EnderecoRequestDTO(
        @NotBlank(message = "O campo CEP é obrigatório.")
        @Size(max = 9, message = "O campo CEP deve ter no máximo 9 caracteres.")
        String cep,

        @NotBlank(message = "O campo bairro é obrigatório.")
        @Size(max = 100, message = "O campo bairro deve ter no máximo 100 caracteres.")
        String bairro,

        @NotNull(message = "O campo número é obrigatório.")
        Integer numero,

        @NotBlank(message = "O campo complemento é obrigatório.")
        @Size(max = 100, message = "O campo complemento deve ter no máximo 100 caracteres.")
        String complemento,

        @NotNull(message = "O campo município é obrigatório.")
        Long idMunicipio
) {
}
