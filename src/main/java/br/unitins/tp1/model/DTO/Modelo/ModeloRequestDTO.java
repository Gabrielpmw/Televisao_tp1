package br.unitins.tp1.model.DTO.Modelo;

import java.time.LocalDate;

public record ModeloRequestDTO(
        String modelo,
        int mesesGarantia,
        LocalDate anoLancamento,
        long idMarca,
        long idCaracteristicas
) {
}
