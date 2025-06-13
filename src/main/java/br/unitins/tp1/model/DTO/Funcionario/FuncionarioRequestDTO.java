package br.unitins.tp1.model.DTO.Funcionario;

public record FuncionarioRequestDTO(
        String nome,
        String cpf,
        String username,
        String senha
) {
}
