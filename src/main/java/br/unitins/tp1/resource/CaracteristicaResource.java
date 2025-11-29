package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.CaracteristicasGerais.CaracteristicasRequestDTO;
import br.unitins.tp1.service.Caracterisicas.CaracteristicaServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.logging.Logger;

@Path("/caracteristicas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CaracteristicaResource {

    @Inject
    CaracteristicaServiceImpl caracteristicaService;

    @Inject
    JsonWebToken jwt;

    private static final Logger logger = Logger.getLogger(CaracteristicaResource.class.getName());

    @POST
    // @RolesAllowed("adm")
    public Response incluir(CaracteristicasRequestDTO dto) {
        // 4. Logs ativados
        logger.info("Nova característica criada: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(caracteristicaService.create(dto)).build();
    }

    @PUT
    // @RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, CaracteristicasRequestDTO dto) {
        // 4. Logs ativados
        logger.info("Característica atualizada: " + id);
        logger.info("Atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        caracteristicaService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    // @RolesAllowed("adm")
    @Path("/{id}/apagar")
    public Response apagar(@PathParam("id") long id) {
        // 4. Logs ativados
        logger.info("id característica deletada: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        caracteristicaService.delete(id);
        return Response.noContent().build();
    }

    @GET
    // @RolesAllowed("adm")
    public Response buscarTodos(@QueryParam("page") @DefaultValue("0") int page,
                                @QueryParam("pageSize") @DefaultValue("10") int pageSize) {

        logger.info("Buscando todas as características (paginado)");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        // 7. Chamadas corretas ao service (com base no Service que já arrumamos)
        return Response.ok(caracteristicaService.findAll(page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", caracteristicaService.count())
                .build();
    }

    @GET
    @Path("/{id}/buscar-por-id")
    // @RolesAllowed("adm")
    public Response buscarPorId(@PathParam("id") long id) {
        // 4. Logs ativados
        logger.info("Buscando característica com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(caracteristicaService.findById(id)).build();
    }

    @GET
    @Path("/nome/{nome}") // Path padronizado (como em MarcaResource)
    // @RolesAllowed("adm")
    public Response buscarPorNome(@PathParam("nome") String nome, // Corrigido de "long id" para "String nome"
                                  @QueryParam("page") @DefaultValue("0") int page,
                                  @QueryParam("pageSize") @DefaultValue("10") int pageSize) {

        logger.info("Buscando característica por nome: " + nome + " (paginado)");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        // 9. Chamadas corretas ao service (com base no Service que já arrumamos)
        return Response.ok(caracteristicaService.findByNomeQuerry(nome, page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", caracteristicaService.count(nome))
                .build();
    }
}