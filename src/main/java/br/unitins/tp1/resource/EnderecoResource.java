package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Endereco.Endereco.EnderecoRequestDTO;
import br.unitins.tp1.service.Endereco.EnderecoServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/endereco")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoServiceImpl enderecoService;

    @POST
    public Response incluir(EnderecoRequestDTO dto){
        return Response.status(Response.Status.CREATED).entity(enderecoService.create(dto)).build();
    }

    @PUT
    @Path("/{id}/atualizar")
    public Response alterar(@PathParam("id") long id, EnderecoRequestDTO dto){
        enderecoService.update(id, dto);

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        enderecoService.delete(id);

        return Response.noContent().build();
    }

    @GET
    @Path("/{id}/buscar-id")
    public Response procurarPorId(@PathParam("id") long id){
        return Response.ok().entity(enderecoService.findById(id)).build();
    }

    @GET
    public Response buscarTodos(){
        return Response.ok().entity(enderecoService.findAll()).build();
    }

    @GET
    @Path("/{cep}/buscar-casas-por-cep")
    public Response buscarPorCep(@PathParam("cep") String cep){
        return Response.ok().entity(enderecoService.findByCEP(cep)).build();
    }

    @GET
    @Path("/{municipio}/buscar-municipio-por-cep")
    public Response buscarMunicipioPorCep(@PathParam("municipio") String cep){
        return Response.ok().entity(enderecoService.findByMunicipio(cep)).build();
    }
}


















