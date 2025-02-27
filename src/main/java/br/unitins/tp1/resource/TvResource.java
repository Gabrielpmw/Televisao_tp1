package br.unitins.tp1.resource;


import java.util.List;

import br.unitins.tp1.model.Televisao;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/tv")
public class TvResource{

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Televisao> buscarTodos(){
        return Televisao.findAll().list();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Televisao incluir(Televisao televisao){
        Televisao tv = new Televisao();

        tv.setMarca(televisao.getMarca());
        tv.setModelo(televisao.getModelo());
        tv.setLinha(televisao.getLinha());
        tv.setAnoLancamento(televisao.getAnoLancamento());

        tv.persist();
        
        return tv;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public void alterar(long id, Televisao televisao){
        Televisao tv = Televisao.findById(id);

        tv.setMarca(televisao.getMarca());
        tv.setModelo(televisao.getModelo());
        tv.setLinha(televisao.getLinha());
        tv.setAnoLancamento(televisao.getAnoLancamento());
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void deletar(long id){
        Televisao.deleteById(id);
    }
}
