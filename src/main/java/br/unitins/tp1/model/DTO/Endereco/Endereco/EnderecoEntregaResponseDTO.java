package br.unitins.tp1.model.DTO.Endereco.Endereco;

import br.unitins.tp1.model.Endereco.EnderecoEntrega;

public record EnderecoEntregaResponseDTO(
        long id,
        String cep,
        String bairro,      // Adicionado conforme sua Entidade
        String complemento,
        int numero,
        String municipio,   // Importante para saber a cidade do snapshot
        String username
) {


    public static EnderecoEntregaResponseDTO valueOf(EnderecoEntrega enderecoEntrega){
        if (enderecoEntrega == null) return null;

        return new EnderecoEntregaResponseDTO(
                enderecoEntrega.getId(),
                enderecoEntrega.getCep(),
                enderecoEntrega.getBairro(),
                enderecoEntrega.getComplemento(),
                enderecoEntrega.getNumero(),

                // MUDANÇA AQUI: Pegando direto do relacionamento em EnderecoEntrega
                enderecoEntrega.getMunicipio().getNome(),

                // Username continua vindo do Pedido -> Usuario
                enderecoEntrega.getPedido().getUsuario().getUsername()
        );
    }
}
