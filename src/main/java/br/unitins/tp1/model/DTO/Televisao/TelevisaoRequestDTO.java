package br.unitins.tp1.model.DTO.Televisao;


public record TelevisaoRequestDTO(String marca,
                                  String modelo,
                                  String resolucao,
                                  Integer polegada,
                                  Integer idTipoTela,
                                  long idFabricante) {

}
