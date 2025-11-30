package br.unitins.tp1.model.DTO.Funcionario;

import br.unitins.tp1.model.Funcionario;

public record FuncionarioResponseDTO(
        long id,
        String username,
        String nome,
        String cpf,
        String sobrenome, // NOVO: Campo adicionado
        String email
) {
    public static FuncionarioResponseDTO valueOf(Funcionario funcionario) {
        // Mapeamos os dados de Funcionario e os novos dados de Usuario
        return new FuncionarioResponseDTO(
                funcionario.getId(),
                funcionario.getUsuario().getUsername(),
                funcionario.getNome(),
                funcionario.getCpf(),
                funcionario.getUsuario().getSobrenome(), // Mapeamento do sobrenome
                funcionario.getUsuario().getEmail()      // Mapeamento do email
        );
    }
}
