package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.service.Televisao.TelevisaoServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.logging.Logger;

@Path("/televisoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelevisaoResource {

    @Inject
    TelevisaoServiceImpl televisaoService;

    @Inject
    JsonWebToken jwt;

    private static final Logger logger = Logger.getLogger(TelevisaoResource.class.getName());

    // =======================================
    // ============ CREATE ===================
    // =======================================
    @POST
    //@RolesAllowed("adm")
    public Response incluir(TelevisaoRequestDTO dto) {
        logger.info("Nova televisão criada: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED)
                .entity(televisaoService.create(dto))
                .build();
    }

    // =======================================
    // ============ UPDATE ===================
    // =======================================
    @PUT
    //@RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, TelevisaoRequestDTO dto) {
        logger.info("Televisão com id: " + id + " atualizada para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        televisaoService.update(id, dto);
        return Response.noContent().build();
    }

    // =======================================
    // ============ DELETE ===================
    // =======================================
    @DELETE
    //@RolesAllowed("adm")
    @Path("/{id}/apagar")
    public Response apagar(@PathParam("id") long id) {
        logger.info("Televisão apagada com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        televisaoService.delete(id);
        return Response.noContent().build();
    }

    // =======================================
    // ============ FIND BY ID ===============
    // =======================================
    @GET
    //@RolesAllowed("adm")
    @Path("/{id}/buscar-por-id")
    public Response buscarPorId(@PathParam("id") long id) {
        logger.info("Buscando televisão com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        Televisao tv = televisaoService.findById(id);
        if (tv == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Televisão não encontrada")
                    .build();
        }
        return Response.ok(TelevisaoResponseDTO.valueOf(tv)).build();
    }

    // =======================================
    // ============ FIND ALL =================
    // =======================================
    @GET
    //@RolesAllowed("adm")
    public Response buscarTodos(@QueryParam("page") @DefaultValue("0") int page,
                                @QueryParam("pageSize") @DefaultValue("10") int pageSize) {

        logger.info("Buscando todas as televisões (paginado)");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        return Response.ok(televisaoService.findAll(page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", televisaoService.count())
                .build();
    }

    // =======================================
    // ======= FIND BY NOME DO MODELO ========
    // =======================================
    @GET
    //@RolesAllowed("adm")
    @Path("/modelo/{nomeModelo}")
    public Response buscarPorModelo(@PathParam("nomeModelo") String nomeModelo,
                                    @QueryParam("page") @DefaultValue("0") int page,
                                    @QueryParam("pageSize") @DefaultValue("10") int pageSize) {

        logger.info("Buscando televisões pelo modelo: " + nomeModelo);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        return Response.ok(televisaoService.findByModelo(nomeModelo, page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", televisaoService.count(nomeModelo))
                .build();
    }

    // =======================================
    // ======= FIND MODELO POR TV ID =========
    // =======================================
    @GET
    //@RolesAllowed("adm")
    @Path("/{idTelevisao}/modelo")
    public Response buscarModeloDaTelevisao(@PathParam("idTelevisao") long idTelevisao) {
        logger.info("Buscando modelo da televisão com id: " + idTelevisao);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        return Response.ok(televisaoService.findTelevisaoByModelo(idTelevisao)).build();
    }
}
