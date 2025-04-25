package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Endereco.Municipio.MunicipioRequestDTO;
import br.unitins.tp1.service.Municipio.MunicipioServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/municipio")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MunicipioResource {

    @Inject
    MunicipioServiceImpl municipioService;

    @POST
    public Response incluir(MunicipioRequestDTO dto){
        return Response.status(Response.Status.CREATED).entity(municipioService.create(dto)).build();
    }

    @PUT
    @Path("/{id}/atualizar-municipio-por-id")
    public Response atualizar(@PathParam("id") long id, MunicipioRequestDTO dto){
        municipioService.update(id, dto);

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/deletar-municipio-por-id")
    public Response deletar(@PathParam("id") long id){
        municipioService.delete(id);

        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/procurar-municipio-por-id")
    public Response procurarPorId(@PathParam("id") long id){
        return Response.ok().entity(municipioService.findById(id)).build();
    }

    @GET
    public Response procurarTodos(){
        return Response.ok().entity(municipioService.findAll()).build();
    }

    @GET
    @Path("{ordenar}/ordenar-municipio")
    public Response ordenarPorNome(){
        return Response.ok().entity(municipioService.ordenarNomes()).build();
    }
}
