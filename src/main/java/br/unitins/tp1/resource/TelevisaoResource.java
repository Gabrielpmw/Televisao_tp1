package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.service.Televisao.TelevisaoServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.logging.Logger;

@Path("/televisao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelevisaoResource {
    @Inject
    TelevisaoServiceImpl service;

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @Inject
    JsonWebToken jwt;

    @POST
    //@RolesAllowed("adm")
    public Response incluir(TelevisaoRequestDTO dto) {
        logger.info("Nova televisão criada: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(service.create(dto)).build();
    }

    @PUT
    //@RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, TelevisaoRequestDTO tv) {
        logger.info("Televisão com id: " + id + " atualizada para " + tv);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        service.update(id, tv);
        return Response.noContent().build();
    }

    @DELETE
    //@RolesAllowed("adm")
    @Path("/{id}/apagar")
    public Response apagar(@PathParam("id") long id) {
        logger.info("Televisão deletada com o id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    //@RolesAllowed("adm")
    @Path("/buscar-por-id/{id}")
    public Response buscarPorId(@PathParam("id") long id){
        logger.info("Buscando televisão pelo id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        Televisao tv = service.findById(id);
        return Response.ok().entity(TelevisaoResponseDTO.valueOf(tv)).build();
    }

    @GET
    //@RolesAllowed("adm")
    public Response buscarTodos() {
        logger.info("Buscando todas as televisões");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(service.findAll()).build();
    }

    @GET
    //@RolesAllowed("adm")
    @Path("/{idTelevisao}/buscar-televisao-por-modelo")
    public Response buscarTelevisaoPorModelo(@PathParam("idTelevisao") long idModelo){
        logger.info("Buscando televião pelo idModelo: " + idModelo);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(service.findTelevisaoByModelo(idModelo)).build();
    }
}
