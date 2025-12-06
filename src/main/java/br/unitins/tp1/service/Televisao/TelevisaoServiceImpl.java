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
import jakarta.ws.rs.NotFoundException; // Adicionado para exceção

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        // --- NOVO: Define a TV como ativa na criação ---
        tv.setAtivo(true);

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

        // O findById só traz se estiver ativo, o que é ideal para o UPDATE
        Televisao tv = televisaoRepository.findById(id);
        if (tv == null)
            throw new NotFoundException("Televisão não encontrada ou inativa.");

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
    // ========== DELETE (SOFT) ======
    // ===============================
    @Transactional
    @Override
    public void delete(long id) {
        // Busca a TV ATIVA. Note que findById só traz se 'ativo = TRUE' (devido ao @Where)
        Televisao tv = televisaoRepository.findById(id);
        if (tv == null)
            throw new NotFoundException("Televisão não encontrada ou já inativa.");

        // CORREÇÃO CRÍTICA: Não confiamos mais no Panache/Hibernate para interceptar o delete.
        // Fazemos o Soft Delete alterando a propriedade diretamente.
        tv.setAtivo(false);
        // A transação (@Transactional) garante que o UPDATE seja executado.

        // Se você quiser manter a chamada original, comente a linha acima e descomente abaixo:
        // televisaoRepository.delete(tv);
    }

    // ===============================
    // ==== RESTAURAR / ATIVAR =======
    // ===============================
    @Transactional
    public void alterarStatus(long id, boolean ativo) {
        // Para alterar o status, precisamos de uma forma de achar a TV, mesmo que inativa.

        // CORREÇÃO: Usamos findByIdOptional, mas precisamos ter certeza que ele não está filtrando.
        // Se a TV estiver inativa, findByIdOptional() retornará vazio se o @Where estiver ativo.
        // Como o ModeloService usou findById (que filtra), vamos tentar o findByIdOptional
        // e se não achar, assumimos que pode estar inativa e a busca precisa ser ajustada no Repo.

        // Se você usa @Where, findById só trará se ativo=true.
        // Para restaurar, precisamos de um método no Repositório que IGNORE o @Where.

        Optional<Televisao> tvOptional;

        if (ativo) {
            // Se estamos ativando, a TV está inativa. Tentamos buscar ela.
            // Aqui, idealmente, você usaria um método customizado do Repo que ignora o @Where.
            // Para simplificar, vamos tentar achar o inativo primeiro.
            tvOptional = televisaoRepository.findAllInativas()
                    .stream()
                    .filter(t -> t.getId() == id)
                    .findFirst();
        } else {
            // Se estamos desativando, a TV está ativa e o findById (abaixo) funciona.
            tvOptional = televisaoRepository.findByIdOptional(id);
        }

        if (tvOptional.isEmpty()) {
            throw new NotFoundException("Televisão não encontrada.");
        }

        Televisao tv = tvOptional.get();

        // Faz a alteração e salva, tanto para restaurar (ativo=true) quanto para deletar (ativo=false)
        tv.setAtivo(ativo);
    }


    // ===============================
    // ========== FINDERS ============
    // ===============================
    @Override
    public Televisao findById(long id) {
        // findById no repositório só traz se ativo (graças ao @Where)
        Televisao tv = televisaoRepository.findById(id);

        if (tv == null)
            throw new NotFoundException("Televisão não encontrada ou inativa.");

        return tv;
    }

    // findAll (ativos) funciona sem alteração
    @Override
    public List<TelevisaoResponseDTO> findAll(int page, int pageSize) {
        PanacheQuery<Televisao> query = televisaoRepository.findAll()
                .page(Page.of(page, pageSize));

        return query.list().stream()
                .map(TelevisaoResponseDTO::valueOf)
                .toList();
    }

    // findByModelo (ativos) funciona sem alteração
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

    // --- NOVOS MÉTODOS PARA LISTAR/CONTAR INATIVOS (LIXEIRA) ---

    public List<TelevisaoResponseDTO> findInativas(int page, int pageSize) {
        // Chama o método específico criado no Repositório
        PanacheQuery<Televisao> query = televisaoRepository.findAllInativas()
                .page(Page.of(page, pageSize));

        return query.list().stream()
                .map(TelevisaoResponseDTO::valueOf)
                .toList();
    }

    public long countInativas() {
        // Conta usando a sintaxe Panache para inativos
        return televisaoRepository.count("ativo", false);
    }

    // ===============================
    // =========== COUNT =============
    // ===============================
    // count (ativos) funciona sem alteração
    @Override
    public long count() {
        return televisaoRepository.findAll().count();
    }

    // count por modelo (ativos) funciona sem alteração
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