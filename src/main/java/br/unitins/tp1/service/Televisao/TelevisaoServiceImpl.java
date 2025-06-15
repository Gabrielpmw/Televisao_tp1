package br.unitins.tp1.service.Televisao;

import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Fabricante;
import br.unitins.tp1.model.Fornecedor;
import br.unitins.tp1.model.Televisao.Dimensao;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.Televisao.TipoResolucao;
import br.unitins.tp1.model.Televisao.TipoTela;
import br.unitins.tp1.repository.DimensaoRepository;
import br.unitins.tp1.repository.FabricanteRepository;
import br.unitins.tp1.repository.TelevisaoRepository;
import br.unitins.tp1.validation.ValidationException;
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

        if (dto == null){
            throw new ValidationException("DTO", "DTO não pode ser null");
        }

        if (dto.idDimensao() <= 0){
            throw new ValidationException("Id dimensão", "Apenas valores positivo para id dimensão");
        }

        if (dto.idFabricante() <= 0){
            throw new ValidationException("Id fabricante", "Apenas valores positivos para id fabricante");
        }

        if (dto.idTipoResolucao() <= 0 || dto.idTipoResolucao() > 4){
            throw new ValidationException("Id tipo resolução", "Tipo de resolução inválida");
        }

        if (dto.idTipoTela() <= 0 || dto.idTipoTela() > 5){
            throw new ValidationException("Id tipo tela", "Tipo de tela inválida");
        }

        tv.setMarca(dto.marca());
        tv.setModelo(dto.modelo());
        tv.setResolucao(TipoResolucao.valueOf(dto.idTipoResolucao()));
        tv.setTipoTela(TipoTela.valueOf(dto.idTipoTela()));
        tv.setValor(dto.valor());
        tv.setEstoque(dto.estoque());

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

        if (dto == null){
            throw new ValidationException("DTO", "DTO não pode ser null");
        }

        if (tv == null){
            throw new ValidationException("Televisão", "Televisão não encontrada");
        }

        if (dto.idDimensao() <= 0){
            throw new ValidationException("Id dimensão", "Apenas valores positivo para id dimensão");
        }

        if (dto.idFabricante() <= 0){
            throw new ValidationException("Id fabricante", "Apenas valores positivos para id fabricante");
        }

        if (dto.idTipoResolucao() <= 0 || dto.idTipoResolucao() > 4){
            throw new ValidationException("Id tipo resolução", "Tipo de resolução inválida");
        }

        if (dto.idTipoTela() <= 0 || dto.idTipoTela() > 5){
            throw new ValidationException("Id tipo tela", "Tipo de tela inválida");
        }

        tv.setMarca(dto.marca());
        tv.setModelo(dto.modelo());
        tv.setResolucao(TipoResolucao.valueOf(dto.idTipoResolucao()));
        tv.setTipoTela(TipoTela.valueOf(dto.idTipoTela()));
        tv.setValor(dto.valor());
        tv.setEstoque(dto.estoque());

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
        return tvrepository.listAll().stream().map(TelevisaoResponseDTO::valueOf).toList();
    }

    @Override
    public TelevisaoResponseDTO findTelevisaoByModelo(String modelo) {
        Televisao televisao = tvrepository.findTelevisaoByModelo(modelo);

        return TelevisaoResponseDTO.valueOf(televisao);
    }
}
