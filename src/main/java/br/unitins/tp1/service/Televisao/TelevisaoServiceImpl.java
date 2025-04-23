package br.unitins.tp1.service.Televisao;

import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Fabricante;
import br.unitins.tp1.model.Televisao.Dimensao;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.Televisao.TipoTela;
import br.unitins.tp1.repository.DimensaoRepository;
import br.unitins.tp1.repository.FabricanteRepository;
import br.unitins.tp1.repository.TelevisaoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TelevisaoServiceImpl implements TelevisaoService {

    @Inject
    protected TelevisaoRepository tvrepository;

    @Inject
    protected FabricanteRepository fabricanteRepository;

    @Inject
    protected DimensaoRepository dimensaoRepository;

    @Transactional
    @Override
    public TelevisaoResponseDTO create(TelevisaoRequestDTO dto) {
        Televisao tv = new Televisao();


        tv.setMarca(dto.marca());
        tv.setModelo(dto.modelo());
        tv.setResolucao(dto.resolucao());
        tv.setTipoTela(TipoTela.valueOf(dto.idTipoTela()));

        Fabricante fabricante = fabricanteRepository.findById(dto.idFabricante());

        tv.setFabricante(fabricante);

        Dimensao dimensao = dimensaoRepository.findById(dto.idDimensao());

        tv.setDimensao(dimensao);

        tvrepository.persist(tv);

        return TelevisaoResponseDTO.valueOf(tv);
    }

    @Transactional
    @Override
    public void update(long id, TelevisaoRequestDTO dto) {
        Televisao tv = tvrepository.findById(id);

        tv.setMarca(dto.marca());
        tv.setModelo(dto.modelo());
        tv.setResolucao(dto.resolucao());
        tv.setTipoTela(TipoTela.valueOf(dto.idTipoTela()));

        Fabricante fabricante = fabricanteRepository.findById(dto.idFabricante());

        tv.setFabricante(fabricante);

        Dimensao dimensao = dimensaoRepository.findById(dto.idDimensao());

        tv.setDimensao(dimensao);
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
    public List<TelevisaoResponseDTO> findAll() {
        return tvrepository.listAll().stream().map(TelevisaoResponseDTO::valueOf).toList();    }

    @Override
    public TelevisaoResponseDTO findByMarca(String marca) {
        return TelevisaoResponseDTO.valueOf(tvrepository.findByMarca(marca));
    }

    @Override
    public List<TelevisaoResponseDTO> findByModelo(String modelo) {
        return tvrepository.findByModelo(modelo).stream().map(TelevisaoResponseDTO::valueOf).toList();
    }

    @Override
    public List<TelevisaoResponseDTO> findByFabricante(long id) {
        return tvrepository.findByFabricante(id).stream().map(TelevisaoResponseDTO::valueOf).toList();
    }
}
