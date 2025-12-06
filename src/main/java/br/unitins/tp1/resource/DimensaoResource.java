package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Televisao.DimensaoRequestDTO;
import br.unitins.tp1.service.Dimensao.DimensaoServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.logging.Logger;

@Path("/dimensao")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DimensaoResource {
    @Inject
    DimensaoServiceImpl dimensaoService;

    @Inject
    JsonWebToken jwt;

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());


    @RolesAllowed("adm")
    @POST
    public Response incluir(DimensaoRequestDTO dto){
        logger.info("Dimensão criada: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(dimensaoService.create(dto)).build();
    }

    @PUT
    @RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, DimensaoRequestDTO dto){
        logger.info("Dimensão atualizada: " + id);
        logger.info("Atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        dimensaoService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed("adm")
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        logger.info("id dimensão deletada: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        dimensaoService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed("adm")
    public Response buscarTodos(){
        logger.info("Buscando todas as dimensões");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(dimensaoService.findAll()).build();
    }

    @GET
    @Path("/{id}/buscar-por-id")
    @RolesAllowed("adm")
    public Response buscarPorId(@PathParam("id") long id){
        logger.info("Buscando dimensão com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(dimensaoService.findById(id)).build();
    }

    @GET
    @Path("/{id}/buscar-televisao-por-id_dimensao")
    @RolesAllowed("adm")
    public Response buscarTelevisaoPorIdDimensao(@PathParam("id") long idDimensao){
        logger.info("Buscando televisão por id de dimensão: " + idDimensao);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(dimensaoService.findTelevisaoByDimensao(idDimensao)).build();
    }
}
