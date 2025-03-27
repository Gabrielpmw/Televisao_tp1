package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.Televisao;
import br.unitins.tp1.service.TelevisaoServiceImpl;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/tv")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TvResource {
    @Inject
    TelevisaoServiceImpl service;

    @POST
    public TelevisaoResponseDTO incluir(TelevisaoRequestDTO dto) {
        return service.create(dto);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") long id, TelevisaoRequestDTO tv) {
        service.update(id, tv);
    }

    @DELETE
    @Path("/{id}")
    public void apagar(@PathParam("id") long id) {
        service.delete(id);
    }

    @GET
    @Path("/{id}")
    public TelevisaoResponseDTO buscarPorId(@PathParam("id") long id){
        Televisao tv = service.findById(id);
        return TelevisaoResponseDTO.valueOf(tv);
    }

    @GET
    public List<TelevisaoResponseDTO> buscarTodos() {
        return service.findAll();
    }

    @GET
    @Path("/marca/{marca}")
    public TelevisaoResponseDTO buscarPorMarca(String marca) {
        return service.findByMarca(marca);
    }

    @GET
    @Path("/modelo/{modelo}")
    public List<TelevisaoResponseDTO> buscarPorModelo(String modelo) {
        return service.findByModelo(modelo);
    }

    @GET
    @Path("fabricante/{id}")
    public List<TelevisaoResponseDTO> buscarPorFabricante(@PathParam("id") long id){
        return service.findByFabricante(id);
    }
}
