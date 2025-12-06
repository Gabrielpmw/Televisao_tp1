package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioCheckDTO;
import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioRequestDTO;
import br.unitins.tp1.service.Municipio.MunicipioServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.logging.Logger;

@Path("/municipio")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MunicipioResource {

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @Inject
    MunicipioServiceImpl municipioService;

    @Inject
    JsonWebToken jwt;

    @POST
    @RolesAllowed("adm")
    public Response incluir(MunicipioRequestDTO dto){
        logger.info("Novo município criado: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(municipioService.create(dto)).build();
    }

    @PUT
    @RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, MunicipioRequestDTO dto){
        logger.info("Município com id: " + id + " atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        municipioService.update(id, dto);

        return Response.noContent().build();
    }

    @DELETE
    @RolesAllowed("adm")
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        logger.info("Municipio deletado com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        municipioService.delete(id);

        return Response.noContent().build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/procurar-id")
    public Response procurarPorId(@PathParam("id") long id){
        logger.info("Procurando município com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(municipioService.findById(id)).build();
    }

    @GET
    //@RolesAllowed("adm")
    public Response procurarTodos(){
        logger.info("Buscando todos os municípios");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(municipioService.findAll()).build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/procurar-endereco")
    public Response procurarEnderecoPorMunicipio(@PathParam("id") long id){
        logger.info("Buscando endereço por id município: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(municipioService.findEnderecoByMunicipio(id)).build();
    }

    @POST
    @Path("/verificar-ou-cadastrar")
    @RolesAllowed({"adm", "cliente"})
    public Response verificarOuCadastrar(MunicipioCheckDTO dto) {
        return Response.ok(municipioService.buscarOuCadastrar(dto)).build();
    }
}
