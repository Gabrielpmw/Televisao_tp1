package br.unitins.tp1.service;

import br.unitins.tp1.model.Televisao;
import br.unitins.tp1.model.DTO.TelevisaoDTO;
import br.unitins.tp1.model.TipoTela;
import br.unitins.tp1.repository.TelevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class TelevisaoServiceImpl implements TelevisaoService {

    @Inject
    protected TelevisaoRepository tvrepository;

    @Transactional
    @Override
    public Televisao create(TelevisaoDTO dto) {
        Televisao tv = new Televisao();

        tv.setMarca(dto.getMarca());
        tv.setModelo(dto.getModelo());
        tv.setResolucao(dto.getResolucao());
        tv.setPolegada(dto.getPolegada());
        tv.setTipoTela(TipoTela.valueOf(dto.getIdTipoTela()));

        tvrepository.persist(tv);

        return tv;
    }

    @Transactional
    @Override
    public void update(long id, TelevisaoDTO dto) {
        Televisao tv = tvrepository.findById(id);

        if (tv != null) {
            tv.setMarca(dto.getMarca());
            tv.setModelo(dto.getModelo());
            tv.setResolucao(dto.getResolucao());
            tv.setPolegada(dto.getPolegada());
            tv.setTipoTela(TipoTela.valueOf(dto.getIdTipoTela()));
        } else {
            throw new NotFoundException("Televisão não encontrada para o ID: " + id);
        }
    }

    @Transactional
    @Override
    public void delete(long id) {
        tvrepository.deleteById(id);
    }

    @Override
    public Televisao findById(long id) {
        return tvrepository.findById(id);
    }

    @Override
    public List<Televisao> findAll() {
        return tvrepository.listAll();
    }

    @Override
    public Televisao findByMarca(String marca) {
        return tvrepository.findByMarca(marca);
    }

    @Override
    public List<Televisao> findByModelo(String modelo) {
        return tvrepository.findByModelo(modelo);
    }
}
