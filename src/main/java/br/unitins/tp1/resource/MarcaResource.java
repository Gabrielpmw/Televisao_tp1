package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Marca.MarcaRequestDTO;
import br.unitins.tp1.service.Marca.MarcaServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.logging.Logger;

@Path("/marca")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {
    @Inject
    MarcaServiceImpl marcaService;

    @Inject
    JsonWebToken jwt;

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @POST
    @RolesAllowed("adm")
    public Response incluir(MarcaRequestDTO dto) {
        logger.info("Nova marca criado: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(marcaService.create(dto)).build();
    }

    @PUT
    @RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, MarcaRequestDTO dto) {
        logger.info("Marca com id: " + id + " atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        marcaService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed("adm")
    @Path("/{id}/apagar")
    public Response apagar(@PathParam("id") long id) {
        logger.info("Marca apagado com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        marcaService.delete(id);
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/buscar-marca-por-id")
    public Response buscarPorId(@PathParam("id") long id){
        logger.info("Procurando marca com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(marcaService.findById(id)).build();
    }

    @GET
    @RolesAllowed("adm")
    public Response buscarTodos(){
        logger.info("Buscando todos as marcas");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(marcaService.findAll()).build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{idMarca}/buscar-modelo-por-marca")
    public Response buscarModeloPorMarca(@PathParam("idMarca") long idMarca){
//        logger.info("Buscando todos as marcas");
//        String username = jwt.getSubject();
//        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(marcaService.findModeloByMarca(idMarca)).build();
    }

}
