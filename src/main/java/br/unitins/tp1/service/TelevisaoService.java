package br.unitins.tp1.service;

import br.unitins.tp1.model.DTO.TelevisaoDTO;
import br.unitins.tp1.model.Televisao;

import java.util.List;

public interface TelevisaoService {
    Televisao create(TelevisaoDTO tv);
    void update(long id, TelevisaoDTO tv);
    void delete(long id);
    Televisao findById(long id);
    List<Televisao> findAll();
    List<Televisao> findByMarca(String marca);
}
