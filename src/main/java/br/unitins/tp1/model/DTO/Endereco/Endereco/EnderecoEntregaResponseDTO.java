package br.unitins.tp1.model.DTO.Endereco.Endereco;

import br.unitins.tp1.model.Endereco.EnderecoEntrega;

public record EnderecoEntregaResponseDTO(
        long id,
        String cep,
        String complemento,
        int numero
) {


    public static EnderecoEntregaResponseDTO valueOf(EnderecoEntrega enderecoEntrega){
        return new EnderecoEntregaResponseDTO(enderecoEntrega.getId(),
                enderecoEntrega.getEndereco().getCep(),
                enderecoEntrega.getEndereco().getComplemento(),
                enderecoEntrega.getEndereco().getNumero());
    }
}
