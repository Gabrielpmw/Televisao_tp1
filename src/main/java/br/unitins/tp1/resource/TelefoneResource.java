package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
import br.unitins.tp1.service.TelefoneServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/telefone")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelefoneResource {
    @Inject
    TelefoneServiceImpl telefoneService;

    @POST
    public Response criar(TelefoneRequestDTO dto){
        return Response.status(Response.Status.CREATED).entity(telefoneService.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") long id, TelefoneRequestDTO dto){
        telefoneService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(long id){
        telefoneService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(long id){
        return Response.ok().entity(telefoneService.findById(id)).build();
    }

    @GET
    public Response buscarTodos(){
        return Response.ok().entity(telefoneService.findAll()).build();
    }
}
