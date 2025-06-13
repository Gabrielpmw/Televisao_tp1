package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Televisao.DimensaoRequestDTO;
import br.unitins.tp1.service.Dimensao.DimensaoServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/dimensao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DimensaoResource {
    @Inject
    DimensaoServiceImpl dimensaoService;

    @RolesAllowed("Adm")
    @POST
    public Response incluir(DimensaoRequestDTO dto){
        return Response.status(Response.Status.CREATED).entity(dimensaoService.create(dto)).build();
    }

    @PUT
    @RolesAllowed("Adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, DimensaoRequestDTO dto){
        dimensaoService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed("Adm")
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        dimensaoService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed("Adm")
    public Response buscarTodos(){
        return Response.ok().entity(dimensaoService.findAll()).build();
    }

    @GET
    @Path("/{id}/buscar-por-id")
    @RolesAllowed("Adm")
    public Response buscarPorId(@PathParam("id") long id){
        return Response.ok().entity(dimensaoService.findById(id)).build();
    }

    @GET
    @Path("/{id}/buscar-televisao-por-id_dimensao")
    @RolesAllowed("Adm")
    public Response buscarTelevisaoPorIdDimensao(@PathParam("id") long idDimensao){
        return Response.ok().entity(dimensaoService.findTelevisaoByDimensao(idDimensao)).build();
    }
}
