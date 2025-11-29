package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Telefone.TelefoneRequestDTO;
import br.unitins.tp1.service.Telefone.TelefoneServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.logging.Logger;

@Path("/telefone")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelefoneResource {
    @Inject
    TelefoneServiceImpl telefoneService;

    @Inject
    JsonWebToken jwt;

    private static final java.util.logging.Logger logger = Logger.getLogger(AuthResource.class.getName());


    @POST
    @RolesAllowed("adm")
    public Response criar(TelefoneRequestDTO dto){
        logger.info("Telefone criado: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(telefoneService.create(dto)).build();
    }

    @PUT
    @RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, TelefoneRequestDTO dto){
        logger.info("Telefone com id: " + id + " atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        telefoneService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed("adm")
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        logger.info("Telefone deletado com o id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        telefoneService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/buscar-telefone-por-id")
    public Response buscarPorId(@PathParam("id") long id){
        logger.info("Procurando telefone com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(telefoneService.findById(id)).build();
    }

    @GET
    @RolesAllowed("adm")
    public Response buscarTodos(){
        logger.info("Buscando todos os telefones");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(telefoneService.findAll()).build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{ddd}/buscar-telefone-por-ddd")
    public Response buscarTelefonesPorDDD(@PathParam("ddd") String ddd){
        logger.info("Buscando telefone com o ddd: " + ddd);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(telefoneService.findTelefonesByDDD(ddd)).build();
    }
}
