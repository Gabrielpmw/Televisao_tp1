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

@Path("/fornecedor")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FornecedorResource {

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @Inject
    FornecedorServiceImpl fornecedorService;

    @Inject
    JsonWebToken jwt;

    @POST
    @RolesAllowed("adm")
    public Response create(FornecedorRequestDTO dto){
        logger.info("Novo fornecedor criado: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(fornecedorService.create(dto)).build();
    }

    @POST
    @RolesAllowed("adm")
    @Path("/{idFornecedor}/associar-marcas")
    public Response marcaForFornecedor(@PathParam("idFornecedor")long idFornecedor, List<Long> idMarcas){
        return Response.status(Response.Status.CREATED).entity(fornecedorService.marcaForFornecedor(idFornecedor, idMarcas)).build();
    }

    @PUT
    @RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, FornecedorRequestDTO dto){
        logger.info("Fornecedor com id: " + id + " atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        fornecedorService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed("adm")
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        logger.info("Fornecedor deletado com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        fornecedorService.delete(id);
        return Response.noContent().build();
    }


    @GET
    @RolesAllowed("adm")
    public Response buscarTodos(){
        logger.info("Buscando todos os fornecedores");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(fornecedorService.findAll()).build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/buscar-fornecedor-por-id")
    public Response buscarPorId(@PathParam("id") long id){
        logger.info("Buscando fornecedor com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(fornecedorService.findById(id)).build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/buscar-televisao-por-id_fornecedor")
    public Response buscarTelevisaoPorIdFornecedor(@PathParam("id") long idFornecedor){
        logger.info("Buscando televisão por id fornecedor: " + idFornecedor);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(fornecedorService.findTelevisaoByFornecedor(idFornecedor)).build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/buscar-fornecedor-por-id_telefone")
    public Response busarFornecedorPorIdTelefone(@PathParam("id") long idTelefone){
        logger.info("Buscando fornecedor por id telefone: " + idTelefone);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(fornecedorService.findFornecedorByTelefone(idTelefone)).build();
    }
}
