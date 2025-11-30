package br.unitins.tp1.model.DTO.Funcionario;

public record FuncionarioDeleteRequestDTO(
        Long idFuncionario,
        String senhaAlvo
) {
}
