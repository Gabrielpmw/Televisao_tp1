package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteRequestDTO;
import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.service.Fabricante.FabricanteServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.logging.Logger;


@Path("/fabricantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResource {
    @Inject
    FabricanteServiceImpl service;

    @Inject
    JsonWebToken jwt;

    private static final Logger logger = Logger.getLogger(FabricanteResource.class.getName());

    @POST
    @RolesAllowed("adm")
    public Response incluir(FabricanteRequestDTO dto) {
        logger.info("Novo fabricante criado: " + dto);
        logger.info("Usuário responsável: " + jwt.getSubject());
        return Response.status(Response.Status.CREATED).entity(service.create(dto)).build();
    }

    @PUT
    @RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, FabricanteRequestDTO dto) {
        logger.info("Atualizando fabricante id: " + id);
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed("adm")
    @Path("/{id}/apagar")
    public Response apagar(@PathParam("id") long id) {
        // Agora este método chama o Soft Delete no service
        logger.info("Desativando (Soft Delete) fabricante id: " + id);
        service.delete(id);
        return Response.noContent().build();
    }

    // --- NOVO ENDPOINT: ATIVAR (RESTAURAR) ---
    @PATCH
    @RolesAllowed("adm")
    @Path("/{id}/ativar")
    public Response ativarFabricante(@PathParam("id") long id) {
        logger.info("Reativando fabricante id: " + id);
        service.alterarStatus(id, true);
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/buscar-fabricante-por-id")
    public Response buscarPorId(@PathParam("id") long id){
        return Response.ok(service.findById(id)).build();
    }

    // --- LISTAGEM PADRÃO (Apenas Ativos) ---
    @GET
    public Response buscarTodos(@QueryParam("page")     @DefaultValue("0")   int page,
                                @QueryParam("pageSize") @DefaultValue("10")  int pageSize) {
        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        // service.count() agora retorna apenas a quantidade de ATIVOS
        return Response.ok(service.findAll(page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", service.count())
                .build();
    }

    // --- NOVO ENDPOINT: LISTAGEM DA LIXEIRA (Apenas Inativos) ---
    @GET
    @Path("/inativos")
    @RolesAllowed("adm") // Importante: Geralmente só ADM vê o que foi apagado
    public Response buscarInativos(@QueryParam("page")     @DefaultValue("0")   int page,
                                   @QueryParam("pageSize") @DefaultValue("10")  int pageSize) {
        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        logger.info("Listando fabricantes inativos/deletados");

        return Response.ok(service.findInativos(page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", service.countInativos()) // Método novo do Service
                .build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/buscar-marca-por-fabricante")
    public Response buscarMarcaPorFabricante(@PathParam("id") long idFabricante){
        return Response.ok(service.findMarcasByFabricante(idFabricante)).build();
    }

    @GET
    @Path("/nome/{nome}")
    @RolesAllowed("adm")
    public Response buscarPorNome(@PathParam("nome") String nome,
                                  @QueryParam("page")     @DefaultValue("0")   int page,
                                  @QueryParam("pageSize") @DefaultValue("10")  int pageSize) {
        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        return Response.ok(service.findByNome(nome, page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", service.count(nome))
                .build();
    }

    @GET
    @Path("/todos")
    @RolesAllowed("adm")
    public Response buscarTodosSemPaginacao(){
        // service.buscarTodos() também foi ajustado para retornar só ativos
        List<FabricanteResponseDTO> listaCompleta = service.buscarTodos();
        return Response.ok(listaCompleta).build();
    }
}
