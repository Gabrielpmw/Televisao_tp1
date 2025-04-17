package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Televisao.DimensaoRequestDTO;
import br.unitins.tp1.service.DimensaoServiceImpl;
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

    @POST
    public Response incluir(DimensaoRequestDTO dto){
        return Response.status(Response.Status.CREATED).entity(dimensaoService.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") long id, DimensaoRequestDTO dto){
        dimensaoService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") long id){
        dimensaoService.delete(id);
        return Response.noContent().build();
    }

    @GET
    public Response buscarTodos(){
        return Response.ok().entity(dimensaoService.findAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") long id){
        return Response.ok().entity(dimensaoService.findById(id)).build();
    }
}
