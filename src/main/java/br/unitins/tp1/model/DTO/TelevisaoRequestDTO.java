package br.unitins.tp1.model.DTO;


public record TelevisaoRequestDTO(String marca,
                                  String modelo,
                                  String resolucao,
                                  Integer polegada,
                                  Integer idTipoTela) {

}
