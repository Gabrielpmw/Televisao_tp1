package br.unitins.tp1.model.DTO.Fabricante;

import br.unitins.tp1.model.DTO.Telefone.TelefoneResponseDTO;
import br.unitins.tp1.model.PessoaJuridica.Fabricante;

import java.time.LocalDate;
import java.util.List;

public record FabricanteResponseDTO(
        long id,
        String razaoSocial,
        String cnpj,
        LocalDate dataAbertura,
        String paisSede,
        boolean ativo,
        List<TelefoneResponseDTO> telefones
) {
    public static FabricanteResponseDTO valueOf(Fabricante fabricante){
        if (fabricante == null) return null;
        return new FabricanteResponseDTO(fabricante.getId(),
                fabricante.getRazaoSocial(),
                fabricante.getCnpj(),
                fabricante.getAnoFundacao(),
                fabricante.getPaisSede(),
                fabricante.getAtivo(),
                fabricante.getTelefones().stream().map(TelefoneResponseDTO::valueOf).toList());
    }
}
