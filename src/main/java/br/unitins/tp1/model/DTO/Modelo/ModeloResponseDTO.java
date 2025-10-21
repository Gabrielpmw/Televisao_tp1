package br.unitins.tp1.model.DTO.Modelo;

import br.unitins.tp1.model.DTO.CaracteristicasGerais.CaracteristicasResponseDTO;
import br.unitins.tp1.model.DTO.Marca.MarcaResponseDTO;
import br.unitins.tp1.model.Modelo;
import br.unitins.tp1.repository.ModeloRepository;
import jakarta.inject.Inject;

import java.time.LocalDate;

public record ModeloResponseDTO(
        String nomeMarca,
        long id,
        String modelo,
        int mesesGarantia,
        LocalDate anoLancamento,
        CaracteristicasResponseDTO caracteristicasResponseDTO
) {

    public static ModeloResponseDTO valueOf(Modelo modelo){
        if (modelo == null) return null;

        return new ModeloResponseDTO(
                modelo.getMarca().getNomeMarca(),
                modelo.getId(),
                modelo.getModelo(),
                modelo.getMesesGarantia(),
                modelo.getAnoLancamento(),
                CaracteristicasResponseDTO.valueOf(modelo.getCaracteristicas()));

    }
}
