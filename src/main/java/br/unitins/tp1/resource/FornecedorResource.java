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
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, FornecedorRequestDTO dto){
        fornecedorService.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        fornecedorService.delete(id);
        return Response.noContent().build();
    }

    @GET
    public Response buscarTodos(){
        return Response.ok().entity(fornecedorService.findAll()).build();
    }

    @GET
    @Path("/{id}/buscar-fornecedor-por-id")
    public Response buscarPorId(@PathParam("id") long id){
        return Response.ok().entity(fornecedorService.findById(id)).build();
    }

    @GET
    @Path("/{id}/buscar-televisao-por-id_fornecedor")
    public Response buscarTelevisaoPorIdFornecedor(@PathParam("id") long idFornecedor){
        return Response.ok().entity(fornecedorService.findTelevisaoByFornecedor(idFornecedor)).build();
    }

    @GET
    @Path("/{id}/buscar-fornecedor-por-id_telefone")
    public Response busarFornecedorPorIdTelefone(@PathParam("id") long idTelefone){
        return Response.ok().entity(fornecedorService.findFornecedorByTelefone(idTelefone)).build();
    }
}
