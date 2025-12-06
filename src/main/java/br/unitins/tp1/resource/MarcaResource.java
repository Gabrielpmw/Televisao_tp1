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
    @RolesAllowed("adm")
    public Response incluir(MarcaRequestDTO dto) {
        logger.info("Nova marca criada: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(marcaService.create(dto)).build();
    }

    @PUT
    @RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, MarcaRequestDTO dto) {
        logger.info("Marca com id: " + id + " atualizada para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        marcaService.update(id, dto);
        return Response.noContent().build();
    }

    // --- SOFT DELETE ---
    @DELETE
    @RolesAllowed("adm")
    @Path("/{id}/apagar")
    public Response apagar(@PathParam("id") long id) {
        logger.info("Desativando (Soft Delete) marca id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        // O service.delete já faz setAtivo(false)
        marcaService.delete(id);

        return Response.noContent().build();
    }

    // --- NOVO ENDPOINT: ATIVAR (RESTAURAR) ---
    @PATCH
    @RolesAllowed("adm")
    @Path("/{id}/ativar")
    public Response ativarMarca(@PathParam("id") long id) {
        logger.info("Reativando marca id: " + id);
        // Chama o novo método do service
        marcaService.alterarStatus(id, true);
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/buscar-marca-por-id")
    public Response buscarPorId(@PathParam("id") long id){
        logger.info("Procurando marca com id: " + id);
        return Response.ok().entity(marcaService.findById(id)).build();
    }

    // --- LISTAGEM PADRÃO (Apenas Ativos) ---
    @GET
    // @RolesAllowed("adm") // Comentei pois geralmente listagens são públicas ou precisam de role específica
    public Response buscarTodos(@QueryParam("page")     @DefaultValue("0")  int page,
                                @QueryParam("pageSize") @DefaultValue("10") int pageSize){

        logger.info("Buscando todas as marcas ativas (paginado)");

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        return Response.ok(marcaService.findAll(page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", marcaService.count()) // Conta apenas ativos
                .build();
    }

    // --- NOVO ENDPOINT: LISTAGEM DA LIXEIRA (Apenas Inativos) ---
    @GET
    @Path("/inativos")
    @RolesAllowed("adm") // Apenas ADM deve ver o que foi excluído
    public Response buscarInativos(@QueryParam("page")     @DefaultValue("0")   int page,
                                   @QueryParam("pageSize") @DefaultValue("10")  int pageSize) {

        logger.info("Listando marcas inativas/deletadas");

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        return Response.ok(marcaService.findInativos(page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", marcaService.countInativos()) // Novo count de inativos
                .build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/buscar-modelo-por-marca")
    public Response buscarModeloPorMarca(@PathParam("id") long idMarca){
        logger.info("Buscando modelos por id da marca: " + idMarca);
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
                .header("X-Total-Count", marcaService.count(nome)) // Conta por nome (apenas ativos)
                .build();
    }
}