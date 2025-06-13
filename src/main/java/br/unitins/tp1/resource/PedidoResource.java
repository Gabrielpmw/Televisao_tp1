package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Pagamento.CartaoRequestDTO;
import br.unitins.tp1.model.DTO.Pedido.PedidoRequestDTO;
import br.unitins.tp1.model.DTO.Pedido.PedidoupdateRequestDTO;
import br.unitins.tp1.service.Pedido.PedidoServiceImpl;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PedidoResource {

    @Inject
    PedidoServiceImpl pedidoService;

    @POST
    @Transactional
    @Path("/{username}/criar-pedido")
    public Response criarPedido(PedidoRequestDTO dto, @PathParam("username") String username){
        return Response.status(Response.Status.CREATED).entity(pedidoService.create(dto, username)).build();
    }

    @PATCH
    @Transactional
    @Path("/{id}/atualizar-pedido")
    public Response atualizarPedido(@PathParam("id")long id ,PedidoupdateRequestDTO dto){
        pedidoService.update_user(id, dto);

        return Response.noContent().build();
    }

    @PUT
    @Transactional
    @Path("/{id}/atualizar-pedido-adm")
    public Response atualizarPedidoAdmin(@PathParam("id") long id, PedidoRequestDTO dto){
        pedidoService.update_admin(id, dto);

        return Response.noContent().build();
    }

    @PATCH
    @Transactional
    @Path("/{id}/atualizar-status")
    public Response atualizarStatusPedido(@PathParam("id") long id, int status){
        pedidoService.updateStatusPedido(id, status);

        return Response.noContent().build();
    }

    @DELETE
    @Transactional
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        pedidoService.delete(id);

        return Response.noContent().build();
    }

    @POST
    @Transactional
    @Path("/{id}/gerar-pix")
    public Response gerarPix(@PathParam("id") long idPedido, String username){
        return Response.status(Response.Status.CREATED).entity(pedidoService.gerarPix(idPedido, username)).build();
    }

    @POST
    @Transactional
    @Path("{id}/gerar-boleto")
    public Response gerarBoleto(@PathParam("id") long idPedido, String username){
        return Response.status(Response.Status.CREATED).entity(pedidoService.gerarBoleto(idPedido, username)).build();
    }

    @PATCH
    @Transactional
    @Path("/{id}/efetuar-pagamento-pix")
    public Response efetuarPagamanentoPix(@PathParam("id") long idPix, String username){
        return Response.ok().entity(pedidoService.registrarPagamentoPix(idPix, username)).build();
    }

    @PATCH
    @Transactional
    @Path("/{id}/efetuar-pagamento-boleto")
    public Response efetuarPagamentoBoleto(@PathParam("id") long idBoleto, String username){
        return Response.ok().entity(pedidoService.registrarPagamentoBoleto(idBoleto, username)).build();
    }

    @POST
    @Transactional
    @Path("/{id}/efetuar-pagamento-cartao")
    public Response efetuarPagamentoCartao(@PathParam("id") long idPedido, CartaoRequestDTO dto){
        return Response.status(Response.Status.CREATED).entity(pedidoService.registrarPagamentoCartao(idPedido, dto)).build();
    }

    @GET
    @Path("/{id}/procurar-id")
    public Response procurarPedidoPorId(@PathParam("id") long idPedido){
        return Response.ok().entity(pedidoService.findById(idPedido)).build();
    }

    @GET
    public Response procurarTodosPedidos(){
        return Response.ok().entity(pedidoService.findAll()).build();
    }

    @GET
    @Path("/{username}/procurar-pedido-username")
    public Response procurarPedidoPorUsername(@PathParam("username") String username){
        return Response.ok().entity(pedidoService.findByUsername(username)).build();
    }
}
