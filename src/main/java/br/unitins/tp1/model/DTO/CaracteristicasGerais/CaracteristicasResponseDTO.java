package br.unitins.tp1.model.DTO.CaracteristicasGerais;

import br.unitins.tp1.model.CaracteristicasGerais;

public record CaracteristicasResponseDTO(
        long id,
        String nome,
        String sistemaOperacional,
        int quantidadeHDMI,
        int quantidadeUSB,
        boolean smartTV
) {
    public static CaracteristicasResponseDTO valueOf(CaracteristicasGerais caracteristicasGerais){
        return new CaracteristicasResponseDTO(caracteristicasGerais.getId(),
                caracteristicasGerais.getNome(),
                caracteristicasGerais.getSistemaOperacional(),
                caracteristicasGerais.getQuantidadeHDMI(),
                caracteristicasGerais.getQuantidadeUSB(),
                caracteristicasGerais.isSmartTV());
    }

}
