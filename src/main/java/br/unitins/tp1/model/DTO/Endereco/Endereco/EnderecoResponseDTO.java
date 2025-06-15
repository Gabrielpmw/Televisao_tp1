package br.unitins.tp1.model.DTO.Endereco.Endereco;

import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioResponseDTO;
import br.unitins.tp1.model.Endereco.Endereco;

public record EnderecoResponseDTO(
        long idEndereco,
        String cep,
        String bairro,
        long numero,
        String complemento,
        MunicipioResponseDTO municipio,
        String proprietário
) {

    public static EnderecoResponseDTO valueOf(Endereco endereco){
        if (endereco == null) return null;

        return new EnderecoResponseDTO(endereco.getId(), endereco.getCep(),
                                        endereco.getBairro(), endereco.getNumero(), endereco.getComplemento(),
                                        MunicipioResponseDTO.valueOf(endereco.getMunicipio()),
                                        endereco.getUsuario().getUsername());
    }
}
