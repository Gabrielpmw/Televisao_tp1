package br.unitins.tp1.model.DTO.Pagamento;

import br.unitins.tp1.model.Pagamento.Cartao;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record CartaoRequestDTO(

        @NotBlank(message = "Titular do cartão deve ser informado")
        String titular,

        @NotBlank(message = "Numero do cartão deve ser informado")
        @Positive(message = "Deve informar apenas valores positivos")
        String numero,

        @NotBlank(message = "Data de validade do cartão deve ser informado")
        @Future(message = "Validade do cartão não aceita. Apenas valores futuro.")
        LocalDateTime dataValidade,

        @NotBlank(message = "CVV do cartão deve ser informado.")
        String cvv
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
