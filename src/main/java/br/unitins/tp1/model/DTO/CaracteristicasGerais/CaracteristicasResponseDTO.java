package br.unitins.tp1.model.DTO.CaracteristicasGerais;

import br.unitins.tp1.model.CaracteristicasGerais;

public record CaracteristicasResponseDTO(
        long id,
        String sistemaOperacional,
        int quantidadeHDMI,
        int quantidadeUSB,
        boolean smartTV
) {
    public static CaracteristicasResponseDTO valueOf(CaracteristicasGerais caracteristicasGerais){
        return new CaracteristicasResponseDTO(caracteristicasGerais.getId(),
                caracteristicasGerais.getSistemaOperacioanl(),
                caracteristicasGerais.getQuantidadeHDMI(),
                caracteristicasGerais.getQuantidadeHDMI(),
                caracteristicasGerais.isSmartTV());
    }

}
