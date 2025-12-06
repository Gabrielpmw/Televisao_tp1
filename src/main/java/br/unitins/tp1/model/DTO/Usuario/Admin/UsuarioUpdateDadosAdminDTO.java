package br.unitins.tp1.model.DTO.Usuario.Admin;

import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UsuarioUpdateDadosAdminDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotBlank(message = "O sobrenome é obrigatório")
        String sobrenome,

        @NotBlank(message = "O CPF é obrigatório")
        @Size(min = 11, max = 14)
        String cpf,

        @Email(message = "Email inválido")
        String email,

        LocalDate dataNascimento,

        TelefoneRequestDTO telefone
) {
}
