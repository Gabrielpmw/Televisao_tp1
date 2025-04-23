package br.unitins.tp1.model.DTO.Fornecedor;

import br.unitins.tp1.model.DTO.Telefone.TelefoneResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Fornecedor;

import java.util.List;

public record FornecedorResponseDTO(
        long id,
        String nome,
        List<TelefoneResponseDTO> telefones
) {
    public static FornecedorResponseDTO valueOf(Fornecedor fornecedor){
        if (fornecedor == null) return null;

        return new FornecedorResponseDTO(
                fornecedor.getId(),
                fornecedor.getNome(),
                fornecedor.getTelefones().stream().map(TelefoneResponseDTO::valueOf).toList());
    }
}
