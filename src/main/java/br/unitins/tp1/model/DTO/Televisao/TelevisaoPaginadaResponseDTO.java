package br.unitins.tp1.model.DTO.Televisao;

import java.util.List;

public record TelevisaoPaginadaResponseDTO(
        List<TelevisaoResponseDTO> televisoes,
        long totalCount
) {
}
