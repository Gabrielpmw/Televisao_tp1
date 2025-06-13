package br.unitins.tp1.service.Pedido;

import br.unitins.tp1.model.DTO.Pagamento.BoletoResponseDTO;
import br.unitins.tp1.model.DTO.Pagamento.CartaoRequestDTO;
import br.unitins.tp1.model.DTO.Pagamento.CartaoResponseDTO;
import br.unitins.tp1.model.DTO.Pagamento.PixResponseDTO;
import br.unitins.tp1.model.DTO.Pedido.PedidoRequestDTO;
import br.unitins.tp1.model.DTO.Pedido.PedidoResponseDTO;
import br.unitins.tp1.model.DTO.Pedido.PedidoupdateRequestDTO;

import java.util.List;

public interface PedidoService {
    PedidoResponseDTO create(PedidoRequestDTO dto, String username);
    void update_user(long id, PedidoupdateRequestDTO dto);
    void update_admin(long id, PedidoRequestDTO dto);
    void updateStatusPedido(long id, int status);
    void delete(long id);
    PixResponseDTO gerarPix(Long idPedido, String username);
    BoletoResponseDTO gerarBoleto(Long idPedido, String username);
    PixResponseDTO registrarPagamentoPix(Long idPix, String username);
    BoletoResponseDTO registrarPagamentoBoleto(Long idBoleto, String username);
    CartaoResponseDTO registrarPagamentoCartao(long idPedido ,CartaoRequestDTO dto);
    PedidoResponseDTO findById(Long id);
    List<PedidoResponseDTO> findAll();
    List<PedidoResponseDTO> findByUsername(String username);
//    List<PedidoResponseDTO> findPedidoByStatusPedido(int idUsuario, int idStatus);
//    List<PedidoResponseDTO> findPedidoByStatuPagamento(int idUsuario, int status);
}
