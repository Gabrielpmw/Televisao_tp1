package br.unitins.tp1.model.DTO.Fornecedor;

import br.unitins.tp1.model.DTO.Telefone.TelefoneResponseDTO;
import br.unitins.tp1.model.PessoaJuridica.Fornecedor;

import java.time.LocalDate;
import java.util.List;

public record FornecedorResponseDTO(
        long id,
        String razaoSocial,
        String cnpj,
        String email,
        List<TelefoneResponseDTO> telefones
) {
    public static FornecedorResponseDTO valueOf(Fornecedor fornecedor){
        if (fornecedor == null) return null;

        return new FornecedorResponseDTO(
                fornecedor.getId(),
                fornecedor.getRazaoSocial(),
                fornecedor.getCnpj(),
                fornecedor.getEmail(),
                fornecedor.getTelefones().stream().map(TelefoneResponseDTO::valueOf).toList());
    }
}
