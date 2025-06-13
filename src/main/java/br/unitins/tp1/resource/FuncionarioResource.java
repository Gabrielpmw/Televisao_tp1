package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Funcionario.FuncionarioRequestDTO;
import br.unitins.tp1.service.Funcionario.FuncionarioServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/funcionario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    @Inject
    FuncionarioServiceImpl funcionarioService;

    @POST
    public Response incluir(FuncionarioRequestDTO dto) {
        return Response.status(Response.Status.CREATED).entity(funcionarioService.create(dto)).build();
    }

    @PUT
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, FuncionarioRequestDTO dto) {
        funcionarioService.update(id, dto);

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id) {
        funcionarioService.delete(id);

        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/procurar-por-id")
    public Response findById(@PathParam("id") Long id) {
        return Response.ok().entity(funcionarioService.findById(id)).build();
    }

    @GET
    public Response procurarTodos() {
        return Response.ok().entity(funcionarioService.findAll()).build();
    }

    @GET
    @Path("/{username}/procurar-por-username")
    public Response procurarPorUsername(@PathParam("username") String username){
        return Response.ok().entity(funcionarioService.findByUsername(username)).build();
    }

}
