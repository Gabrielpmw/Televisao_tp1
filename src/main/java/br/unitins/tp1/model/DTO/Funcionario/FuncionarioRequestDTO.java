package br.unitins.tp1.model.DTO.Funcionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record FuncionarioRequestDTO(

        @NotBlank(message = "Nome do funcionário deve ser informado")
        String nome,

        @NotBlank(message = "CPF do funcionário deve ser informado")
        String cpf,

        @NotBlank(message = "Username do funcionário deve ser informado")
        String username,

        @NotBlank(message = "Senha do usuário deve ser informado")
        String senha,

        @NotBlank(message = "Sobrenome do funcionário deve ser informado")
        String sobrenome,

        @NotBlank(message = "Email do funcionário deve ser informado")
        @Email(message = "Email em formato inválido")
        String email
) {
}
