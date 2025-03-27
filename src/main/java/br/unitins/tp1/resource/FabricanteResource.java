package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Fabricante.FabricanteRequestDTO;
import br.unitins.tp1.model.DTO.Fabricante.FabricanteResponseDTO;
import br.unitins.tp1.service.FabricanteServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/fabricante")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResource {
    @Inject
    FabricanteServiceImpl serviceFabricante;

    @POST
    public FabricanteResponseDTO incluir(FabricanteRequestDTO dto) {
        return serviceFabricante.create(dto);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") long id, FabricanteRequestDTO dto) {
        serviceFabricante.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void apagar(@PathParam("id") long id) {
        serviceFabricante.delete(id);
    }

    @GET
    @Path("/{id}")
    public FabricanteResponseDTO buscarPorId(@PathParam("id") long id){
        return serviceFabricante.findById(id);
    }

    @GET
    public List<FabricanteResponseDTO> buscarTodos(){
        return serviceFabricante.findAll();
    }
}
