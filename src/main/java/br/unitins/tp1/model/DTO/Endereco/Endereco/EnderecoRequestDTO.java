package br.unitins.tp1.model.DTO.Endereco.Endereco;

public record EnderecoRequestDTO(
        String cep,
        String bairro,
        int numero,
        String complemento,
        long idMunicipio
) {
}
