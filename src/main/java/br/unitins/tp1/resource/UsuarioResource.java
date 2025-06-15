package br.unitins.tp1.resource;


import br.unitins.tp1.model.DTO.Usuario.UsuarioCreateRequestDTO;
import br.unitins.tp1.model.DTO.Usuario.UsuarioUpdateRequestDTO;
import br.unitins.tp1.service.Usersss.UsuarioServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private static final Logger logger = Logger.getLogger(UsuarioResource.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UsuarioResource.class);


    @Inject
    UsuarioServiceImpl usuarioService;

    @Inject
    JsonWebToken jwt;

    @POST
    public Response criarUsuario(UsuarioCreateRequestDTO dto){
        logger.info("Criando novo usuário");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(usuarioService.create(dto)).build();
    }

    @PATCH
    @RolesAllowed({"user"})
    @Path("/atualizar")
    public Response atualizarusuario(UsuarioUpdateRequestDTO dto){
        logger.info("Usuário atualizado: " + dto.usernameAntigo());
        logger.info("Atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        usuarioService.update(dto);

        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed({"user"})
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        String username1 = jwt.getSubject();
        logger.info("Usuário responsável: " + username1);
        usuarioService.delete(id);

        return Response.noContent().build();
    }

    @GET
    @RolesAllowed({"adm"})
    @Path("/{id}/procurar-id")
    public Response procurarPorId(@PathParam("id") long id){
        logger.info("Buscando usuário com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(usuarioService.findById(id)).build();
    }

    @GET
    @RolesAllowed({"adm"})
    public Response buscarTodos(){
        logger.info("Buscando todos os usuários");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(usuarioService.findAll()).build();
    }

    @GET
    @RolesAllowed({"adm"})
    @Path("/{username}/buscar-username")
    public Response buscarPorUsername(@PathParam("username") String username){
        logger.info("Buscando usuário com username de:" + username);
        String username1 = jwt.getSubject();
        logger.info("Usuário responsável: " + username1);
        return Response.ok().entity(usuarioService.findByUsername(username)).build();
    }
}
