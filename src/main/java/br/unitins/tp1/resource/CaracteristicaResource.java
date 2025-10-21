package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.CaracteristicasGerais.CaracteristicasRequestDTO;
import br.unitins.tp1.service.Caracterisicas.CaracteristicaServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/caracteristica")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CaracteristicaResource {

    @Inject
    CaracteristicaServiceImpl caracteristicaService;

//    @RolesAllowed("adm")
    @POST
    public Response incluir(CaracteristicasRequestDTO dto){
//        logger.info("Dimensão criada: " + dto);
//        String username = jwt.getSubject();
//        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(caracteristicaService.create(dto)).build();
    }

    @PUT
    //@RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, CaracteristicasRequestDTO dto){
//        logger.info("Dimensão atualizada: " + id);
//        logger.info("Atualizado para: " + dto);
//        String username = jwt.getSubject();
//        logger.info("Usuário responsável: " + username);
          caracteristicaService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
//    @RolesAllowed("adm")
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
//        logger.info("id dimensão deletada: " + id);
//        String username = jwt.getSubject();
//        logger.info("Usuário responsável: " + username);
//        dimensaoService.delete(id);
        return Response.noContent().build();
    }

    @GET
//    @RolesAllowed("adm")
    public Response buscarTodos(){
//        logger.info("Buscando todas as dimensões");
//        String username = jwt.getSubject();
//        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(caracteristicaService.findAll()).build();
    }

    @GET
    @Path("/{id}/buscar-por-id")
//    @RolesAllowed("adm")
    public Response buscarPorId(@PathParam("id") long id){
//        logger.info("Buscando dimensão com id: " + id);
//        String username = jwt.getSubject();
//        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(caracteristicaService.findById(id)).build();
    }
}
