package br.unitins.tp1.model.DTO.CaracteristicasGerais;

public record CaracteristicasRequestDTO(
        String sistemaOperacional,
        int quantidadeHDMI,
        int quantidadeUSB,
        boolean smartTV
) {
}
