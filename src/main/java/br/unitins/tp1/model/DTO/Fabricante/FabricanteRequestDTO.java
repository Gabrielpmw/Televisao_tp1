package br.unitins.tp1.model.DTO.Fabricante;

public record FabricanteRequestDTO(
        String nome,
        String cnpj,
        String paisSede,
        String telefone
){

}
