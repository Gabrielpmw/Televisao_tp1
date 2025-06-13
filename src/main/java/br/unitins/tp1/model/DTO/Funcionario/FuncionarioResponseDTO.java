package br.unitins.tp1.model.DTO.Funcionario;

import br.unitins.tp1.model.Funcionario;

public record FuncionarioResponseDTO(
        long id,
        String username,
        String nome,
        String cpf
) {
    public static FuncionarioResponseDTO valueOf(Funcionario funcionario) {
        return new FuncionarioResponseDTO(funcionario.getId(),
                funcionario.getUsuario().getUsername(),
                funcionario.getNome(),
                funcionario.getCpf());
    }
}
