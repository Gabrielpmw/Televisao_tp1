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
    FabricanteServiceImpl serviceFabricante;

    @Inject
    JsonWebToken jwt;

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @POST
    //@RolesAllowed("adm")
    public Response incluir(FabricanteRequestDTO dto) {
        logger.info("Novo fabricante criado: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(serviceFabricante.create(dto)).build();
    }

    @PUT
    //@RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, FabricanteRequestDTO dto) {
        logger.info("Fabricante com id: " + id + " atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        serviceFabricante.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    //@RolesAllowed("adm")
    @Path("/{id}/apagar")
    public Response apagar(@PathParam("id") long id) {
        logger.info("Fabricante apagado com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        serviceFabricante.delete(id);
        return Response.noContent().build();
    }

    @GET
   // @RolesAllowed("adm")
    @Path("/{id}/buscar-fabricante-por-id")
    public Response buscarPorId(@PathParam("id") long id){
        logger.info("Procurando fabricante com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(serviceFabricante.findById(id)).build();
    }

    @GET
    public Response buscarTodos(@QueryParam("page")     @DefaultValue("0")   int page,
                                @QueryParam("pageSize") @DefaultValue("10")  int pageSize) {
        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        return Response.ok(serviceFabricante.findAll(page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", serviceFabricante.count())
                .build();
    }

    @GET
    //@RolesAllowed("adm")
    @Path("/{id}/buscar-marca-por-fabricante")
    public Response buscarMarcaPorFabricante(@PathParam("id") long idFabricante){
        logger.info("Buscando marca por id fabricante: " + idFabricante);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(serviceFabricante.findMarcasByFabricante(idFabricante)).build();
    }

    @GET
    @Path("/nome/{nome}")
    //@RolesAllowed("adm")
    public Response buscarPorNome(@PathParam("nome") String nome,
                                  @QueryParam("page")     @DefaultValue("0")   int page,
                                  @QueryParam("pageSize") @DefaultValue("10")  int pageSize) {
        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        return Response.ok(serviceFabricante.findByNome(nome, page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", serviceFabricante.count(nome))
                .build();
    }

    @GET
    @Path("/todos")
    //@RolesAllowed("adm")
    public Response buscarTodos(){
        List<FabricanteResponseDTO> listaCompleta = serviceFabricante.buscarTodos();
        return Response.ok(listaCompleta).build();
    }
}
