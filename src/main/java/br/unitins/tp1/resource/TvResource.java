package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.Televisao;
import br.unitins.tp1.service.TelevisaoServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/tv")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TvResource {
    @Inject
    TelevisaoServiceImpl service;

    @POST
    public Response incluir(TelevisaoRequestDTO dto) {
        return Response.status(Response.Status.CREATED).entity(service.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") long id, TelevisaoRequestDTO tv) {
        service.update(id, tv);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response apagar(@PathParam("id") long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") long id){
        Televisao tv = service.findById(id);
        return Response.ok().entity(TelevisaoResponseDTO.valueOf(tv)).build();
    }

    @GET
    public Response buscarTodos() {
        return Response.ok().entity(service.findAll()).build();
    }

    @GET
    @Path("/marca/{marca}")
    public Response buscarPorMarca(String marca) {
        return Response.ok().entity(service.findByMarca(marca)).build();
    }

    @GET
    @Path("/modelo/{modelo}")
    public Response buscarPorModelo(String modelo) {
        return Response.ok().entity(service.findByModelo(modelo)).build();
    }

    @GET
    @Path("fabricante/{id}")
    public Response buscarPorFabricante(@PathParam("id") long id){
        return Response.ok().entity(service.findByFabricante(id)).build();
    }
}
