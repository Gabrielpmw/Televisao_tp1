package br.unitins.tp1.repository;

import br.unitins.tp1.model.Modelo;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.model.Televisao.TipoTela;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class TelevisaoRepository implements PanacheRepository<Televisao> {

    // --- MÉTODOS EXISTENTES (Já respeitam @Where para ativos) ---

    public Modelo findModeloByTelevisao(Long televisaoId) {
        // Usa findByIdOptional, que respeita o @Where (traz só ativo)
        Televisao tv = findByIdOptional(televisaoId).orElse(null);
        if (tv != null) {
            return tv.getModelo();
        }
        return null;
    }

    public PanacheQuery<Televisao> findByNomeModelo(String nomeModelo) {
        if (nomeModelo == null || nomeModelo.isBlank()) {
            // find("1 = 1") respeita o @Where (traz só ativo)
            return find("1 = 1");
        }

        String query = "SELECT t FROM Televisao t WHERE UPPER(t.modelo.modelo) LIKE ?1";
        String parametro = nomeModelo.toUpperCase() + "%";

        // find(query, params) respeita o @Where (traz só ativo)
        return find(query, parametro);
    }

    public List<String> findAllMarcas() {
        // Não envolve a Entidade Televisao, então não precisa de filtro 'ativo'
        return getEntityManager()
                .createQuery("SELECT DISTINCT m.nomeMarca FROM Marca m ORDER BY m.nomeMarca", String.class)
                .getResultList();
    }

    // --- MÉTODOS AJUSTADOS PARA FILTRO EXPLÍCITO DE ATIVOS ---

    // Método de filtro com paginação e ordenação
    public List<Televisao> findByFiltros(List<String> marcas, Integer maxPolegada, List<TipoTela> tipos, String sort, int page, int pageSize) {

        StringBuilder hql = new StringBuilder("SELECT t FROM Televisao t ");
        hql.append("LEFT JOIN t.modelo m ");
        hql.append("LEFT JOIN m.marca ma ");
        hql.append("LEFT JOIN t.dimensao d ");
        // Adiciona a cláusula explícita para ativos.
        hql.append("WHERE t.ativo = TRUE ");

        Map<String, Object> params = new HashMap<>();

        // 1. Filtro de Marca
        if (marcas != null && !marcas.isEmpty()) {
            hql.append("AND ma.nomeMarca IN (:marcas) ");
            params.put("marcas", marcas);
        }

        // 2. Filtro de Polegada (Menor ou igual)
        if (maxPolegada != null) {
            hql.append("AND d.polegada <= :maxPolegada ");
            params.put("maxPolegada", maxPolegada);
        }

        // 3. Filtro de Tipo de Tela
        if (tipos != null && !tipos.isEmpty()) {
            hql.append("AND t.tipoTela IN (:tipos) ");
            params.put("tipos", tipos);
        }

        // 4. Ordenação (Sort)
        if ("menorPreco".equals(sort)) {
            hql.append("ORDER BY t.valor ASC ");
        } else if ("maiorPreco".equals(sort)) {
            hql.append("ORDER BY t.valor DESC ");
        } else {
            hql.append("ORDER BY t.id DESC "); // Padrão
        }

        return find(hql.toString(), params)
                .page(Page.of(page, pageSize))
                .list();
    }

    // Contagem para paginação dos filtros
    public long countByFiltros(List<String> marcas, Integer maxPolegada, List<TipoTela> tipos) {

        StringBuilder hql = new StringBuilder("SELECT count(t) FROM Televisao t ");
        hql.append("LEFT JOIN t.modelo m ");
        hql.append("LEFT JOIN m.marca ma ");
        hql.append("LEFT JOIN t.dimensao d ");
        // Adiciona a cláusula explícita para ativos.
        hql.append("WHERE t.ativo = TRUE ");

        Map<String, Object> params = new HashMap<>();

        if (marcas != null && !marcas.isEmpty()) {
            hql.append("AND ma.nomeMarca IN (:marcas) ");
            params.put("marcas", marcas);
        }

        if (maxPolegada != null) {
            hql.append("AND d.polegada <= :maxPolegada ");
            params.put("maxPolegada", maxPolegada);
        }

        if (tipos != null && !tipos.isEmpty()) {
            hql.append("AND t.tipoTela IN (:tipos) ");
            params.put("tipos", tipos);
        }

        return find(hql.toString(), params).count();
    }

    // --- NOVO MÉTODO PARA BUSCAR ITENS INATIVOS ---

    public PanacheQuery<Televisao> findAllInativas() {
        // Busca onde 'ativo' é explicitamente FALSE, anulando o filtro @Where.
        return find("ativo = FALSE");
    }
}