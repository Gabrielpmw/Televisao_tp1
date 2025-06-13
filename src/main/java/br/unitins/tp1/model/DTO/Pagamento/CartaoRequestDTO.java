package br.unitins.tp1.model.DTO.Pagamento;

import br.unitins.tp1.model.Pagamento.Cartao;

import java.time.LocalDateTime;

public record CartaoRequestDTO(
        String titular,
        String numero,
        LocalDateTime dataValidade,
        String  cvv
) {
    public static Cartao convertToCartao(CartaoRequestDTO dto){
        Cartao cartao = new Cartao();

        cartao.setTitular(dto.titular());
        cartao.setNumero(dto.numero);
        cartao.setDataValidade(dto.dataValidade);
        cartao.setCvv(dto.cvv);

        return cartao;
    }
}
