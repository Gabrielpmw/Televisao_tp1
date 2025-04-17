package br.unitins.tp1.model.DTO.Televisao;


public record TelevisaoRequestDTO(String marca,
                                  String modelo,
                                  String resolucao,
                                  Integer idTipoTela,
                                  long idDimensao,
                                  long idFabricante) {

}
