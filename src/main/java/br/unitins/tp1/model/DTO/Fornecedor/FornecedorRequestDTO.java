package br.unitins.tp1.model.DTO.Fornecedor;

import java.util.List;

public record FornecedorRequestDTO(
        String nome,
        List<Long> idTelefone,
        List<Long> idTelevisao
) {
}
