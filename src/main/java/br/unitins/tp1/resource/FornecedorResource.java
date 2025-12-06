package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.service.Fornecedor.FornecedorServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.List;
import java.util.logging.Logger;

// 1. Path atualizado para o plural, por consistência
@Path("/fornecedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FornecedorResource {

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @Inject
    FornecedorServiceImpl fornecedorService;

    @Inject
    JsonWebToken jwt;

    @POST
    //@RolesAllowed("adm")
    public Response create(FornecedorRequestDTO dto){
        logger.info("Novo fornecedor criado: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(fornecedorService.create(dto)).build();
    }

    @POST
    //@RolesAllowed("adm")
    @Path("/{idFornecedor}/associar-marcas")
    public Response marcaForFornecedor(@PathParam("idFornecedor")long idFornecedor, List<Long> idMarcas){
        return Response.status(Response.Status.CREATED).entity(fornecedorService.marcaForFornecedor(idFornecedor, idMarcas)).build();
    }

    @PUT
    //@RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, FornecedorRequestDTO dto){
        logger.info("Fornecedor com id: " + id + " atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        fornecedorService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    //@RolesAllowed("adm")
    // 2. Path atualizado para /apagar, por consistência
    @Path("/{id}/apagar")
    public Response deletar(@PathParam("id") long id){
        logger.info("Fornecedor deletado com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        fornecedorService.delete(id);
        return Response.noContent().build();
    }


    @GET
    //@RolesAllowed("adm")
    // 3. Método buscarTodos ATUALIZADO com paginação
    public Response buscarTodos(@QueryParam("page")     @DefaultValue("0")   int page,
                                @QueryParam("pageSize") @DefaultValue("10")  int pageSize) {

        logger.info("Buscando todos os fornecedores (paginado)");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        // 4. Assumindo que o service terá findAll(page, pageSize) e count()
        return Response.ok(fornecedorService.findAll(page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", fornecedorService.count())
                .build();
    }

    @GET
    //@RolesAllowed("adm")
    @Path("/{id}/buscar-fornecedor-por-id")
    public Response buscarPorId(@PathParam("id") long id){
        logger.info("Buscando fornecedor com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(fornecedorService.findById(id)).build();
    }



    @GET
    //@RolesAllowed("adm")
    @Path("/{id}/buscar-marca-por-fornecedor")
    public Response buscarMarcaPorFornecedor(@PathParam("id") long idFornecedor){
        logger.info("Buscando marca por id fornecedor: " + idFornecedor);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(fornecedorService.findMarcaByFornecedor(idFornecedor)).build();
    }

    @GET
    @Path("/nome/{nome}")
    public Response buscarPorNome(@PathParam("nome") String nome,
                                  @QueryParam("page")     @DefaultValue("0")   int page,
                                  @QueryParam("pageSize") @DefaultValue("10")  int pageSize) {
        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        return Response.ok(fornecedorService.findByNome(nome, page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", fornecedorService.count(nome))
                .build();
    }
}