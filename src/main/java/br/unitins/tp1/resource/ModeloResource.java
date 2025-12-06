package br.unitins.tp1.resource;

// Verifique se todos os imports necessários estão aqui
import br.unitins.tp1.model.DTO.Modelo.ModeloRequestDTO;
import br.unitins.tp1.model.DTO.Modelo.ModeloResponseDTO; // Import adicionado
import br.unitins.tp1.service.Modelo.ModeloServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List; // Import adicionado
import java.util.logging.Logger;

@Path("/modelos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModeloResource {

    @Inject
    ModeloServiceImpl modeloService;

    @Inject
    JsonWebToken jwt;

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @POST
    @RolesAllowed("adm")
    public Response incluir(ModeloRequestDTO dto) {
        logger.info("Novo modelo criado: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(modeloService.create(dto)).build();
    }

    @PUT
    @RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, ModeloRequestDTO dto) {
        logger.info("Modelo com id: " + id + " atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        modeloService.update(id, dto);
        return Response.noContent().build();
    }

    // --- SOFT DELETE (Desativar) ---
    @DELETE
    @RolesAllowed("adm")
    @Path("/{id}/apagar")
    public Response apagar(@PathParam("id") long id) {
        logger.info("Desativando (Soft Delete) modelo id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        // O service agora faz apenas setAtivo(false)
        modeloService.delete(id);

        return Response.noContent().build();
    }

    // --- NOVO ENDPOINT: ATIVAR (RESTAURAR) ---
    @PATCH
    @RolesAllowed("adm")
    @Path("/{id}/ativar")
    public Response ativarModelo(@PathParam("id") long id) {
        logger.info("Reativando modelo id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        // Chama o novo método do service para restaurar
        modeloService.alterarStatus(id, true);

        return Response.noContent().build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/buscar-modelo-por-id")
    public Response buscarPorId(@PathParam("id") long id){
        logger.info("Procurando modelo com id: " + id);
        return Response.ok().entity(modeloService.findById(id)).build();
    }

    // --- LISTAGEM PADRÃO (Apenas Ativos) ---
    @GET
    @RolesAllowed("adm")
    public Response buscarTodos(@QueryParam("page")     @DefaultValue("0")  int page,
                                @QueryParam("pageSize") @DefaultValue("10") int pageSize){

        logger.info("Buscando todos os modelos ativos (paginado)");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        // O service.findAll e service.count agora filtram por ativo=true
        return Response.ok(modeloService.findAll(page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", modeloService.count())
                .build();
    }

    // --- NOVO ENDPOINT: LISTAGEM DA LIXEIRA (Apenas Inativos) ---
    @GET
    @Path("/inativos")
    @RolesAllowed("adm") // Geralmente restrito a ADM
    public Response buscarInativos(@QueryParam("page")     @DefaultValue("0")   int page,
                                   @QueryParam("pageSize") @DefaultValue("10")  int pageSize) {

        logger.info("Listando modelos inativos/deletados");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        return Response.ok(modeloService.findInativos(page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", modeloService.countInativos()) // Novo método count
                .build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/nome/{nome}")
    public Response buscarPorNome(@PathParam("nome") String nome,
                                  @QueryParam("page")     @DefaultValue("0")  int page,
                                  @QueryParam("pageSize") @DefaultValue("10") int pageSize) {

        logger.info("Buscando modelos ativos por nome: " + nome);

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        return Response.ok(modeloService.findByNome(nome, page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", modeloService.count(nome))
                .build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/marca/{idMarca}")
    public Response buscarPorMarca(@PathParam("idMarca") Long idMarca) {
        logger.info("Buscando modelos ativos por ID da marca: " + idMarca);
        List<ModeloResponseDTO> lista = modeloService.findByMarca(idMarca);
        return Response.ok(lista).build();
    }
}