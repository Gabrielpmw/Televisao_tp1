package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoRequestDTO;
import br.unitins.tp1.service.Endereco.EnderecoServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

@Path("/endereco")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    JsonWebToken jwt;

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(EnderecoResource.class);

    @Inject
    EnderecoServiceImpl enderecoService;

    @POST
    @RolesAllowed({"adm", "cliente"})
    public Response incluir(EnderecoRequestDTO dto){
        logger.info("Endereço criado: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        return Response.status(Response.Status.CREATED).entity(enderecoService.create(dto)).build();
    }

    @RolesAllowed({"adm", "cliente"})
    @PUT
    @Path("/{id}/atualizar")
    public Response alterar(@PathParam("id") long id, EnderecoRequestDTO dto){
        logger.info("id endereço alterado: " + id);
        logger.info("Atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        enderecoService.update(id, dto);

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/deletar")
    @RolesAllowed({"adm", "cliente"})
    public Response deletar(@PathParam("id") long id){
        logger.info("Id endereço: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        enderecoService.delete(id);

        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/buscar-id")
    @RolesAllowed("adm")
    public Response procurarPorId(@PathParam("id") long id){
        logger.info("Pricurando endereço por id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(enderecoService.findById(id)).build();
    }

    @GET
    @RolesAllowed("adm")
    public Response buscarTodos(){
        logger.info("Buscando todos os endereços");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(enderecoService.findAll()).build();
    }

    @GET
    @Path("/{cep}/buscar-casa-por-cep")
    @RolesAllowed("adm")
    public Response buscarPorCep(@PathParam("cep") String cep){
        logger.info("Buscando endereços por cep:" + cep);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(enderecoService.findByCEP(cep)).build();
    }

    @GET
    @Path("/buscar-endereco-user")
    @RolesAllowed({"adm", "cliente"})
    public Response buscarCasaPorUsername(){
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        return Response.ok().entity(enderecoService.findMyEndereco()).build();
    }
}


















