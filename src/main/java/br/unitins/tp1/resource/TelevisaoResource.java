package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.service.Televisao.TelevisaoServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/televisao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelevisaoResource {
    @Inject
    TelevisaoServiceImpl service;

    @POST
    public Response incluir(TelevisaoRequestDTO dto) {
        return Response.status(Response.Status.CREATED).entity(service.create(dto)).build();
    }

    @PUT
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, TelevisaoRequestDTO tv) {
        service.update(id, tv);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/apagar")
    public Response apagar(@PathParam("id") long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/buscar-televisao-por-id")
    public Response buscarPorId(@PathParam("id") long id){
        Televisao tv = service.findById(id);
        return Response.ok().entity(TelevisaoResponseDTO.valueOf(tv)).build();
    }

    @GET
    public Response buscarTodos() {
        return Response.ok().entity(service.findAll()).build();
    }

    @GET
    @Path("/{modelo}/buscar-televisao-por-modelo")
    public Response buscarTelevisaoPorModelo(@PathParam("modelo") String modelo){
        return Response.ok().entity(service.findTelevisaoByModelo(modelo)).build();
    }
}
