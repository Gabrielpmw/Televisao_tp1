package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Endereco.Estado.EstadoRequestDTO;
import br.unitins.tp1.service.Estado.EstadoServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/Estado")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @Inject
    EstadoServiceImpl estadoService;

    @POST
    public Response incluir(EstadoRequestDTO dto){
        return Response.status(Response.Status.CREATED).entity(estadoService.create(dto)).build();
    }

    @PUT
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, EstadoRequestDTO dto){
         estadoService.update(id, dto);

         return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        estadoService.delete(id);

        return Response.noContent().build();
    }

    @GET
    public Response buscarTodos(){
        return Response.ok().entity(estadoService.findAll()).build();
    }

    @GET
    @Path("/{id}/buscar-id")
    public Response buscarPorId(@PathParam("id") long id){
        return Response.ok().entity(estadoService.findById(id)).build();
    }

    @GET
    @Path("/{nome}/buscar-nome")
    public Response buscarPorNome(@PathParam("nome") String nome){
        return Response.ok().entity(estadoService.findByNome(nome)).build();
    }

    @GET
    @Path("/{id}/buscar-municipio")
    public Response buscarMunicipioPorEstado(@PathParam("id") long id){
        return Response.ok().entity(estadoService.findMunicipioByEstado(id)).build();
    }
}
