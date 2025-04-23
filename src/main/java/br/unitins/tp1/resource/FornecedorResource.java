package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Fornecedor.FornecedorRequestDTO;
import br.unitins.tp1.service.Fornecedor.FornecedorServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/fornecedor")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FornecedorResource {

    @Inject
    FornecedorServiceImpl fornecedorService;

    @POST
    public Response create(FornecedorRequestDTO dto){
        return Response.status(Response.Status.CREATED).entity(fornecedorService.create(dto)).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") long id, FornecedorRequestDTO dto){
        fornecedorService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(long id){
        fornecedorService.delete(id);
        return Response.noContent().build();
    }

    @GET
    public Response buscarTodos(){
        return Response.ok().entity(fornecedorService.findAll()).build();
    }
}
