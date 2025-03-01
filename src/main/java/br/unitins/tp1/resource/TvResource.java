package br.unitins.tp1.resource;


import java.util.List;

import br.unitins.tp1.model.Televisao;
import br.unitins.tp1.repository.TelevisaoRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/tv")
public class TvResource {

    @Inject
    protected TelevisaoRepository tvrepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Televisao> buscarTodos() {
        return tvrepository.findAll().list();
    }

    @GET
    @Path("/search/marca/{marca}")
    @Produces(MediaType.APPLICATION_JSON)
    public Televisao buscarPorMarca(String marca) {
        return tvrepository.findByMarca(marca);
    }

    @GET
    @Path("/search/marcas/{polegada}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Televisao> buscarPorPolegada_lista(@PathParam("polegada") int polegada) {
        return tvrepository.findByPolegada(polegada);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Televisao incluir(Televisao televisao) {
        Televisao tv = new Televisao();

        tv.setMarca(televisao.getMarca());
        tv.setPolegada(televisao.getPolegada());
        tv.setTipoTela(televisao.getTipoTela());
        tv.setResolucao(televisao.getResolucao());

        tvrepository.persist(tv);

        return tv;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void alterar(long id, Televisao televisao) {
        Televisao tv = tvrepository.findById(id);

        tv.setMarca(televisao.getMarca());
        tv.setPolegada(televisao.getPolegada());
        tv.setTipoTela(televisao.getTipoTela());
        tv.setResolucao(televisao.getResolucao());
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deletar(long id) {
        tvrepository.deleteById(id);
    }
}
