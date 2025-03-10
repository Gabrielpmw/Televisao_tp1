package br.unitins.tp1.resource;

import br.unitins.tp1.model.Televisao;
import br.unitins.tp1.model.DTO.TelevisaoDTO;
import br.unitins.tp1.service.TelevisaoServiceImpl;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/tv")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TvResource {
    @Inject
    TelevisaoServiceImpl service;

    @GET
    public List<Televisao> buscarTodos() {
        return service.findAll();
    }

    @GET
    @Path("/marca/{marca}")
    public Televisao buscarPorMarca(String marca) {
        return service.findByMarca(marca);
    }

    @GET
    @Path("/modelo/{modelo}")
    public List<Televisao> buscarPorModelo(String modelo) {
        return service.findByModelo(modelo);
    }

    @POST
    public Televisao incluir(TelevisaoDTO dto) {
        return service.create(dto);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") long id, TelevisaoDTO tv) {
        service.update(id, tv);
    }

    @DELETE
    @Path("/{id}")
    public void apagar(@PathParam("id") long id) {
        service.delete(id);
    }


}
