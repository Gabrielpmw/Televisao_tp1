package br.unitins.tp1.model.DTO.Modelo;

import br.unitins.tp1.model.DTO.Marca.MarcaResponseDTO;
import br.unitins.tp1.model.Modelo;

import java.time.LocalDate;

public record ModeloResponseDTO(
        long id,
        String modelo,
        int mesesGarantia,
        LocalDate anoLancamento,
        MarcaResponseDTO marcaResponseDTO
) {
    public static ModeloResponseDTO valueOf(Modelo modelo){
        if (modelo == null) return null;

        return new ModeloResponseDTO(modelo.getId(),
                modelo.getModelo(),
                modelo.getMesesGarantia(),
                modelo.getAnoLancamento(),
                MarcaResponseDTO.valueOf(modelo.getMarca()));

    }
}
