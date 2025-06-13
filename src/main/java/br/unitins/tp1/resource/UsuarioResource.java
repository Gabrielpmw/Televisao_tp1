package br.unitins.tp1.resource;


import br.unitins.tp1.model.DTO.Usuario.UsuarioCreateRequestDTO;
import br.unitins.tp1.model.DTO.Usuario.UsuarioUpdateRequestDTO;
import br.unitins.tp1.service.Usersss.UsuarioServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioServiceImpl usuarioService;

    @Inject
    JsonWebToken jwt;

    @POST
    public Response criarUsuario(UsuarioCreateRequestDTO dto){
        return Response.status(Response.Status.CREATED).entity(usuarioService.create(dto)).build();
    }

    @PATCH
    @Path("/atualizar")
    public Response atualizarusuario(UsuarioUpdateRequestDTO dto){
        usuarioService.update(dto);

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        usuarioService.delete(id);

        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/procurar-id")
    public Response procurarPorId(@PathParam("id") long id){
        return Response.ok().entity(usuarioService.findById(id)).build();
    }

    @GET
    public Response buscarTodos(){
        return Response.ok().entity(usuarioService.findAll()).build();
    }

    @GET
    @Path("/{username}/buscar-username")
    public Response buscarPorUsername(@PathParam("username") String username){
        return Response.ok().entity(usuarioService.findByUsername(username)).build();
    }
}
