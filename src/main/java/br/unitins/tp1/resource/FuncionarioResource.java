package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Funcionario.FuncionarioRequestDTO;
import br.unitins.tp1.service.Funcionario.FuncionarioServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.logging.Logger;

@Path("/funcionario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    @Inject
    FuncionarioServiceImpl funcionarioService;

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @Inject
    JsonWebToken jwt;

    @POST
    @RolesAllowed("adm")
    public Response incluir(FuncionarioRequestDTO dto) {
        logger.info("Novo funcionário criado: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(funcionarioService.create(dto)).build();
    }

    @PUT
    @RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, FuncionarioRequestDTO dto) {
        logger.info("Funcionário com id: " + id + " atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        funcionarioService.update(id, dto);

        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed("adm")
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id) {
        logger.info("Funcionário deletado com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        funcionarioService.delete(id);

        return Response.noContent().build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/procurar-por-id")
    public Response findById(@PathParam("id") Long id) {
        logger.info("Procurando funcionário com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(funcionarioService.findById(id)).build();
    }

    @GET
    @RolesAllowed("adm")
    public Response procurarTodos() {
        logger.info("Procurando todos os funcionários");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(funcionarioService.findAll()).build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{username}/procurar-por-username")
    public Response procurarPorUsername(@PathParam("username") String username){
        logger.info("Procurando funcionário por username: " + username);
        String username1 = jwt.getSubject();
        logger.info("Usuário responsável: " + username1);
        return Response.ok().entity(funcionarioService.findByUsername(username)).build();
    }

}
