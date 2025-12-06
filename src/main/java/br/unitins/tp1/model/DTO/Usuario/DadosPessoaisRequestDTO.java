package br.unitins.tp1.model.DTO.Usuario;

import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record DadosPessoaisRequestDTO(
        @Size(min = 2, max = 60, message = "O nome deve ter entre 2 e 60 caracteres")
        String nome,

        @Size(min = 2, max = 60, message = "O sobrenome deve ter entre 2 e 60 caracteres")
        String sobrenome,

        @Email(message = "O e-mail deve ser válido")
        String email,

        TelefoneRequestDTO telefoneRequestDTO,

        @Past(message = "A data de nascimento deve ser no passado")
        LocalDate dataNascimento,

        @NotBlank(message = "O CPF é obrigatório")
        @Size(min = 11, max = 14, message = "O CPF deve ter 11 caracteres (se apenas números) ou 14 (se formatado)")
        String cpf
) {
}
