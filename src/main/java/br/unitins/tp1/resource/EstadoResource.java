package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Endereco.Estado.EstadoRequestDTO;
import br.unitins.tp1.service.Estado.EstadoServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.logging.Logger;

@Path("/estados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstadoResource {

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @Inject
    EstadoServiceImpl estadoService;

    @Inject
    JsonWebToken jwt;

    @POST
    //@RolesAllowed("adm")
    public Response incluir(EstadoRequestDTO dto){
        logger.info("Novo estado criado: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(estadoService.create(dto)).build();
    }

    @PUT
    //@RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, EstadoRequestDTO dto){
        logger.info("Estado com id: " + id + ", atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
         estadoService.update(id, dto);

         return Response.noContent().build();
    }

    @DELETE
    //@RolesAllowed("adm")
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        logger.info("Estado apagado com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        estadoService.delete(id);

        return Response.noContent().build();
    }

    @GET
    //@RolesAllowed("adm")
    public Response buscarTodos(){
        logger.info("Buscando todos os Estados");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(estadoService.findAll()).build();
    }

    @GET
    //@RolesAllowed("adm")
    @Path("/{id}/buscar-id")
    public Response buscarPorId(@PathParam("id") long id){
        logger.info("Buscando estado com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(estadoService.findById(id)).build();
    }

    @GET
    //@RolesAllowed("adm")
    @Path("/{nome}/buscar-nome")
    public Response buscarPorNome(@PathParam("nome") String nome){
        logger.info("Buscando estado por nome de: " + nome);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(estadoService.findByNome(nome)).build();
    }

    @GET
    //@RolesAllowed("adm")
    @Path("/{id}/buscar-municipio")
    public Response buscarMunicipioPorEstado(@PathParam("id") long id){
        logger.info("Buscando município por estado id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(estadoService.findMunicipioByEstado(id)).build();
    }
}
