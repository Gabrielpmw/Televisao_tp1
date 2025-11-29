package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Pagamento.CartaoRequestDTO;
import br.unitins.tp1.model.DTO.Pedido.PedidoRequestDTO;
import br.unitins.tp1.model.DTO.Pedido.PedidoupdateRequestDTO;
import br.unitins.tp1.service.Pedido.PedidoServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.logging.Logger;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoServiceImpl pedidoService;

    @Inject
    JsonWebToken jwt;

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());
    @POST
    @Transactional
    @RolesAllowed("cliente")
    @Path("/criar-pedido")
    public Response criarPedido(PedidoRequestDTO dto){
        String username = jwt.getSubject();
        logger.info("Novo pedido criado para o usuário: " + username);
        logger.info("Pedido criado: " + dto);

        return Response.status(Response.Status.CREATED).entity(pedidoService.create(dto)).build();
    }

    @PATCH
    @Transactional
    @RolesAllowed("cliente")
    @Path("/{id}/atualizar-pedido")
    public Response atualizarPedido(@PathParam("id")long id ,PedidoupdateRequestDTO dto){
        String username = jwt.getSubject();
        logger.info("Pedido com id: " + id + " atualizado para: " + dto);
        logger.info("Usuário responsável: " + username);
        pedidoService.update_user(id, dto);

        return Response.noContent().build();
    }

    @PUT
    @Transactional
    @RolesAllowed("adm")
    @Path("/{id}/atualizar-pedido-adm")
    public Response atualizarPedidoAdmin(@PathParam("id") long id, PedidoRequestDTO dto){
        logger.info("Pedido com id: " + id + " atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        pedidoService.update_admin(id, dto);

        return Response.noContent().build();
    }

    @PATCH
    @Transactional
    @RolesAllowed("adm")
    @Path("/{id}/atualizar-status")
    public Response atualizarStatusPedido(@PathParam("id") long id, int status){
        logger.info("Pedido com id: " + id + " atualizado para o status pedido: " + status);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        pedidoService.updateStatusPedido(id, status);

        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @RolesAllowed("adm")
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        logger.info("Pedido deletado com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        pedidoService.delete(id);

        return Response.noContent().build();
    }

    @POST
    @Transactional
    @RolesAllowed("cliente")
    @Path("/{id}/gerar-pix")
    public Response gerarPix(@PathParam("id") long idPedido){
        logger.info("Gerando pix para o pedido: " + idPedido);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        return Response.status(Response.Status.CREATED).entity(pedidoService.gerarPix(idPedido)).build();
    }

    @POST
    @Transactional
    @RolesAllowed("cliente")
    @Path("{id}/gerar-boleto")
    public Response gerarBoleto(@PathParam("id") long idPedido){
        logger.info("Gerando boleto para o pedido: " + idPedido);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(pedidoService.gerarBoleto(idPedido)).build();
    }

    @PATCH
    @Transactional
    @RolesAllowed("cliente")
    @Path("/{id}/efetuar-pagamento-pix")
    public Response efetuarPagamanentoPix(@PathParam("id") long idPix){
        logger.info("Pagamento efetuado para o pix: " + idPix);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        return Response.ok().entity(pedidoService.registrarPagamentoPix(idPix)).build();
    }

    @PATCH
    @Transactional
    @RolesAllowed("cliente")
    @Path("/{id}/efetuar-pagamento-boleto")
    public Response efetuarPagamentoBoleto(@PathParam("id") long idBoleto){
        logger.info("Pagamento efetuado para o boleto: " + idBoleto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        return Response.ok().entity(pedidoService.registrarPagamentoBoleto(idBoleto)).build();
    }

    @POST
    @Transactional
    @RolesAllowed("cliente")
    @Path("/{id}/efetuar-pagamento-cartao")
    public Response efetuarPagamentoCartao(@PathParam("id") long idPedido, CartaoRequestDTO dto){
        logger.info("Pagamento efetuado com o cartão: " + dto);
        logger.info("Pedido: " + idPedido);

        return Response.status(Response.Status.CREATED).entity(pedidoService.registrarPagamentoCartao(idPedido, dto)).build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/procurar-id")
    public Response procurarPedidoPorId(@PathParam("id") long idPedido){
        logger.info("Procurando pedido com id: " + idPedido);

        return Response.ok().entity(pedidoService.findById(idPedido)).build();
    }

    @GET
    @RolesAllowed("adm")
    public Response procurarTodosPedidos(){
        logger.info("Procurando todos os pedidos");

        return Response.ok().entity(pedidoService.findAll()).build();
    }

    @GET
    @RolesAllowed({"cliente", "adm"})
    @Path("/{username}/procurar-pedido-username")
    public Response procurarPedidoPorUsername(@PathParam("username") String username){
        logger.info("Procurando pedido do usuário: " + username);

        return Response.ok().entity(pedidoService.findPedidosByUsername(username)).build();
    }

    @GET
    @RolesAllowed("cliente")
    @Path("/meus-pedidos")
    public Response procurarMeusPedidos(){
        String username = jwt.getSubject();
        logger.info("Buscando pedidos dos token: " + username);
        return Response.ok().entity(pedidoService.findMyPedidos()).build();
    }
}
