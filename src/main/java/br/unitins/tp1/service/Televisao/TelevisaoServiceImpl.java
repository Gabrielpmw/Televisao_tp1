package br.unitins.tp1.service.Televisao;

import br.unitins.tp1.model.DTO.Modelo.ModeloResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Modelo;
import br.unitins.tp1.model.Televisao.Dimensao;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.Televisao.TipoResolucao;
import br.unitins.tp1.model.Televisao.TipoTela;
import br.unitins.tp1.repository.DimensaoRepository;
import br.unitins.tp1.repository.ModeloRepository;
import br.unitins.tp1.repository.TelevisaoRepository;
import br.unitins.tp1.validation.ValidationException;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TelevisaoServiceImpl implements TelevisaoService {

    @Inject
    TelevisaoRepository televisaoRepository;

    @Inject
    DimensaoRepository dimensaoRepository;

    @Inject
    ModeloRepository modeloRepository;

    // ===============================
    // ========== CREATE =============
    // ===============================
    @Transactional
    @Override
    public TelevisaoResponseDTO create(TelevisaoRequestDTO dto) {
        if (dto == null)
            throw new ValidationException("DTO", "DTO não pode ser null");

        Televisao tv = new Televisao();

        Modelo modelo = modeloRepository.findById(dto.idModelo());
        if (modelo == null)
            throw new ValidationException("Modelo", "Modelo não encontrado");

        if (dto.idTipoResolucao() <= 0 || dto.idTipoResolucao() > 4)
            throw new ValidationException("Id tipo resolução", "Tipo de resolução inválida");

        if (dto.idTipoTela() <= 0 || dto.idTipoTela() > 5)
            throw new ValidationException("Id tipo tela", "Tipo de tela inválida");

        tv.setModelo(modelo);
        tv.setResolucao(TipoResolucao.valueOf(dto.idTipoResolucao()));
        tv.setTipoTela(TipoTela.valueOf(dto.idTipoTela()));
        tv.setValor(dto.valor());
        tv.setEstoque(dto.estoque());
        tv.setDescricao(dto.descricao());
        Dimensao dimensao = new Dimensao();
        dimensao.setAltura(dto.altura());
        dimensao.setComprimento(dto.largura());
        dimensao.setPolegada(dto.polegada());

        dimensaoRepository.persist(dimensao);
        tv.setDimensao(dimensao);

        televisaoRepository.persist(tv);

        return TelevisaoResponseDTO.valueOf(tv);
    }

    // ===============================
    // ========== UPDATE =============
    // ===============================
    @Transactional
    @Override
    public void update(long id, TelevisaoRequestDTO dto) {
        if (dto == null)
            throw new ValidationException("DTO", "DTO não pode ser null");

        Televisao tv = televisaoRepository.findById(id);
        if (tv == null)
            throw new ValidationException("Televisão", "Televisão não encontrada");

        Modelo modelo = modeloRepository.findById(dto.idModelo());
        if (modelo == null)
            throw new ValidationException("Modelo", "Modelo não encontrado");

        if (dto.idTipoResolucao() <= 0 || dto.idTipoResolucao() > 4)
            throw new ValidationException("Id tipo resolução", "Tipo de resolução inválida");

        if (dto.idTipoTela() <= 0 || dto.idTipoTela() > 5)
            throw new ValidationException("Id tipo tela", "Tipo de tela inválida");

        tv.setModelo(modelo);
        tv.setResolucao(TipoResolucao.valueOf(dto.idTipoResolucao()));
        tv.setTipoTela(TipoTela.valueOf(dto.idTipoTela()));
        tv.setValor(dto.valor());
        tv.setEstoque(dto.estoque());

        Dimensao dimensao = tv.getDimensao();
        dimensao.setAltura(dto.altura());
        dimensao.setComprimento(dto.largura());
        dimensao.setPolegada(dto.polegada());
    }

    // ===============================
    // ========== DELETE =============
    // ===============================
    @Transactional
    @Override
    public void delete(long id) {
        televisaoRepository.deleteById(id);
    }

    // ===============================
    // ========== FINDERS ============
    // ===============================
    @Override
    public Televisao findById(long id) {
        return televisaoRepository.findById(id);
    }

    @Override
    public List<TelevisaoResponseDTO> findAll(int page, int pageSize) {
        PanacheQuery<Televisao> query = televisaoRepository.findAll()
                .page(Page.of(page, pageSize));

        return query.list().stream()
                .map(TelevisaoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<TelevisaoResponseDTO> findByModelo(String nomeModelo, int page, int pageSize) {
        PanacheQuery<Televisao> query = televisaoRepository.findByNomeModelo(nomeModelo)
                .page(Page.of(page, pageSize));

        return query.list().stream()
                .map(TelevisaoResponseDTO::valueOf)
                .toList();
    }

    @Override
    public ModeloResponseDTO findTelevisaoByModelo(long idTelevisao) {
        return ModeloResponseDTO.valueOf(televisaoRepository.findModeloByTelevisao(idTelevisao));
    }

    // ===============================
    // =========== COUNT =============
    // ===============================
    @Override
    public long count() {
        return televisaoRepository.findAll().count();
    }

    @Override
    public long count(String nomeModelo) {
        return televisaoRepository.findByNomeModelo(nomeModelo).count();
    }

    // ===============================
    // ========= FILTROS =============
    // ===============================

    public List<String> findAllMarcas() {
        return televisaoRepository.findAllMarcas();
    }

    // ALTERADO: Adicionado 'sort' e removido 'minPolegada'
    public List<TelevisaoResponseDTO> findByFiltros(List<String> marcas, Integer maxPolegada, List<String> tiposRaw, String sort, int page, int pageSize) {

        // Converte Lista de Strings (Front) para Lista de Enums (Banco)
        List<TipoTela> tiposEnum = new ArrayList<>();
        if (tiposRaw != null) {
            for (String tipo : tiposRaw) {
                try {
                    // Tenta converter String "LED" para Enum TipoTela.LED
                    tiposEnum.add(TipoTela.valueOf(tipo));
                } catch (IllegalArgumentException e) {
                    // Ignora se vier um tipo inválido
                }
            }
        }

        // Passa o 'sort' e apenas o 'maxPolegada' para o repositório
        return televisaoRepository.findByFiltros(marcas, maxPolegada, tiposEnum, sort, page, pageSize)
                .stream()
                .map(TelevisaoResponseDTO::valueOf)
                .toList();
    }

    // ALTERADO: Removido 'minPolegada'
    public long countByFiltros(List<String> marcas, Integer maxPolegada, List<String> tiposRaw) {

        // Mesma lógica de conversão para o Count
        List<TipoTela> tiposEnum = new ArrayList<>();
        if (tiposRaw != null) {
            for (String tipo : tiposRaw) {
                try {
                    tiposEnum.add(TipoTela.valueOf(tipo));
                } catch (IllegalArgumentException e) {
                    // Ignora
                }
            }
        }

        // Passa apenas o 'maxPolegada'
        return televisaoRepository.countByFiltros(marcas, maxPolegada, tiposEnum);
    }
}