package br.unitins.tp1.model.DTO.Pagamento;

import br.unitins.tp1.model.Pagamento.Cartao;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull; // Importação correta para objetos (LocalDate)

import java.time.LocalDate;

public record CartaoRequestDTO(

        @NotBlank(message = "Titular do cartão deve ser informado")
        String titular,

        @NotBlank(message = "Numero do cartão deve ser informado")
        // REMOVIDO: @Positive, pois o campo 'numero' é uma String.
        String numero,

        @NotNull(message = "Data de validade do cartão deve ser informado") // CORREÇÃO: Usando @NotNull em vez de @NotBlank
        @Future(message = "Validade do cartão não aceita. Apenas valores futuro.")
        LocalDate dataValidade,

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