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

// 1. Path atualizado para o plural
@Path("/marcas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MarcaResource {
    @Inject
    MarcaServiceImpl marcaService;

    @Inject
    JsonWebToken jwt;

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @POST
    //@RolesAllowed("adm")
    public Response incluir(MarcaRequestDTO dto) {
        logger.info("Nova marca criada: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(marcaService.create(dto)).build();
    }

    @PUT
    //@RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, MarcaRequestDTO dto) {
        logger.info("Marca com id: " + id + " atualizada para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        marcaService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    //@RolesAllowed("adm")
    @Path("/{id}/apagar")
    public Response apagar(@PathParam("id") long id) {
        logger.info("Marca apagada com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        marcaService.delete(id);
        return Response.noContent().build();
    }

    @GET
    //@RolesAllowed("adm")
    @Path("/{id}/buscar-marca-por-id")
    public Response buscarPorId(@PathParam("id") long id){
        logger.info("Procurando marca com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(marcaService.findById(id)).build();
    }

    // 2. Método buscarTodos ATUALIZADO com paginação
    @GET
    //@RolesAllowed("adm")
    public Response buscarTodos(@QueryParam("page")     @DefaultValue("0")  int page,
                                @QueryParam("pageSize") @DefaultValue("10") int pageSize){

        logger.info("Buscando todas as marcas (paginado)");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        // 3. Assumindo que o service terá findAll(page, pageSize) e count()
        return Response.ok(marcaService.findAll(page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", marcaService.count())
                .build();
    }

    // 4. Path padronizado e logs ativados
    @GET
    //@RolesAllowed("adm")
    @Path("/{id}/buscar-modelo-por-marca")
    public Response buscarModeloPorMarca(@PathParam("id") long idMarca){
        logger.info("Buscando modelos por id da marca: " + idMarca);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(marcaService.findModeloByMarca(idMarca)).build();
    }

    @GET
    @Path("/nome/{nome}")
    public Response buscarPorNome(@PathParam("nome") String nome,
                                  @QueryParam("page")     @DefaultValue("0")  int page,
                                  @QueryParam("pageSize") @DefaultValue("10") int pageSize) {

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        return Response.ok(marcaService.findMarcaByModelo(nome, page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", marcaService.count(nome))
                .build();
    }
}