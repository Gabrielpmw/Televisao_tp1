package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteRequestDTO;
import br.unitins.tp1.service.Fabricante.FabricanteServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/fabricante")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResource {
    @Inject
    FabricanteServiceImpl serviceFabricante;

    @POST
    public Response incluir(FabricanteRequestDTO dto) {
        return Response.status(Response.Status.CREATED).entity(serviceFabricante.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") long id, FabricanteRequestDTO dto) {
        serviceFabricante.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response apagar(@PathParam("id") long id) {
        serviceFabricante.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") long id){
        return Response.ok().entity(serviceFabricante.findById(id)).build();
    }

    @GET
    public Response buscarTodos(){
        return Response.ok().entity(serviceFabricante.findAll()).build();
    }
}
