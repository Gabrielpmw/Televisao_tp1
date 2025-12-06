package br.unitins.tp1.model.DTO.Funcionario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FuncionarioUpdateDadosDTO(
        @NotNull(message = "O ID do funcionário deve ser informado.")
        Long idFuncionario,

        @NotBlank(message = "Nome do funcionário deve ser informado")
        String nome,

        @NotBlank(message = "CPF do funcionário deve ser informado")
        String cpf,

        @NotBlank(message = "Sobrenome do funcionário deve ser informado")
        String sobrenome,

        @NotBlank(message = "Email do funcionário deve ser informado")
        @Email(message = "Email em formato inválido")
        String email,

        // NOVO CAMPO DE SEGURANÇA: Senha atual do Funcionário ALVO
        @NotBlank(message = "A senha atual do funcionário é obrigatória para alteração.")
        String senhaAtualAlvo // Senha do funcionário que será alterado
) {
}
