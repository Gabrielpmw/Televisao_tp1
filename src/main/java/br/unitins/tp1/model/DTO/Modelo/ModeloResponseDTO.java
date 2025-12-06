package br.unitins.tp1.model.DTO.Modelo;

import br.unitins.tp1.model.DTO.CaracteristicasGerais.CaracteristicasResponseDTO;
import br.unitins.tp1.model.DTO.Marca.MarcaResponseDTO;
import br.unitins.tp1.model.Modelo;
import br.unitins.tp1.repository.ModeloRepository;
import jakarta.inject.Inject;

import java.time.LocalDate;

public record ModeloResponseDTO(
        long id,
        String modelo,
        long idMarca,
        String marca,
        int mesesGarantia,
        LocalDate anoLancamento,
        CaracteristicasResponseDTO caracteristicasResponseDTO,
        Boolean ativo
) {

    public static ModeloResponseDTO valueOf(Modelo modelo){
        if (modelo == null) return null;

        return new ModeloResponseDTO(
                modelo.getId(),
                modelo.getModelo(),
                modelo.getMarca().getId(),
                modelo.getMarca().getNomeMarca(),
                modelo.getMesesGarantia(),
                modelo.getAnoLancamento(),
                CaracteristicasResponseDTO.valueOf(modelo.getCaracteristicas()),
                modelo.getAtivo());

    }
}
