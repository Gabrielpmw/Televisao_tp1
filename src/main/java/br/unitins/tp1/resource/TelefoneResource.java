package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
import br.unitins.tp1.service.Telefone.TelefoneServiceImpl;
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
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, TelefoneRequestDTO dto){
        telefoneService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        telefoneService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/buscar-telefone-por-id")
    public Response buscarPorId(@PathParam("id") long id){
        return Response.ok().entity(telefoneService.findById(id)).build();
    }

    @GET
    public Response buscarTodos(){
        return Response.ok().entity(telefoneService.findAll()).build();
    }

    @GET
    @Path("/{ddd}/buscar-telefone-por-ddd")
    public Response buscarTelefonesPorDDD(@PathParam("ddd") String ddd){
        return Response.ok().entity(telefoneService.findTelefonesByDDD(ddd)).build();
    }


}
