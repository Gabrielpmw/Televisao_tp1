package br.unitins.tp1.service.Modelo;

import br.unitins.tp1.model.CaracteristicasGerais;
import br.unitins.tp1.model.DTO.CaracteristicasGerais.CaracteristicasResponseDTO;
import br.unitins.tp1.model.DTO.Modelo.ModeloRequestDTO;
import br.unitins.tp1.model.DTO.Modelo.ModeloResponseDTO;
import br.unitins.tp1.model.Marca;
import br.unitins.tp1.model.Modelo;
import br.unitins.tp1.repository.CaracteristicasGeraisRepository;
import br.unitins.tp1.repository.MarcaRepository;
import br.unitins.tp1.repository.ModeloRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
// 'stream' e 'Collectors' podem já estar implícitos, mas é bom garantir
import java.util.stream.Collectors;


@ApplicationScoped
public class ModeloServiceImpl implements ModeloService{


    @Inject
    ModeloRepository modeloRepository;

    @Inject
    MarcaRepository marcaRepository;
    @Inject
    CaracteristicasGeraisRepository caracteristicasGeraisRepository;

    @Override
    @Transactional
    public ModeloResponseDTO create(ModeloRequestDTO dto) {
        Modelo modelo = new Modelo();
        CaracteristicasGerais caracteristicasGerais = caracteristicasGeraisRepository.findById(dto.idCaracteristicas());
        Marca marca = marcaRepository.findById(dto.idMarca());

        modelo.setModelo(dto.modelo());
        modelo.setMesesGarantia(dto.mesesGarantia());
        modelo.setAnoLancamento(dto.anoLancamento());
        modelo.setCaracteristicas(caracteristicasGerais);
        modelo.setMarca(marca);

        // Garante que nasce ativo (embora o default na entidade já seja true)
        modelo.setAtivo(true);

        modeloRepository.persist(modelo);

        return ModeloResponseDTO.valueOf(modelo);
    }

    @Override
    @Transactional
    public void update(long id, ModeloRequestDTO dto) {
        Modelo modelo = modeloRepository.findById(id);
        if (modelo == null) throw new NotFoundException("Modelo não encontrado");

        Marca marca = marcaRepository.findById(dto.idMarca());
        CaracteristicasGerais caracteristicasGerais = caracteristicasGeraisRepository.findById(dto.idCaracteristicas());

        modelo.setModelo(dto.modelo());
        modelo.setMesesGarantia(dto.mesesGarantia());
        modelo.setAnoLancamento(dto.anoLancamento());
        modelo.setMarca(marca);
        modelo.setCaracteristicas(caracteristicasGerais);
    }

    // --- SOFT DELETE (Desativar) ---
    @Override
    @Transactional
    public void delete(long id) {
        // Não deletamos do banco, apenas mudamos o status
        Modelo modelo = modeloRepository.findById(id);
        if (modelo != null) {
            modelo.setAtivo(false);
        } else {
            throw new NotFoundException("Modelo não encontrado para exclusão");
        }
    }

    // --- RESTAURAR (Ativar) ---
    // Você precisa adicionar 'alterarStatus' na interface ModeloService
    @Transactional
    public void alterarStatus(long id, boolean ativo) {
        Modelo modelo = modeloRepository.findById(id);
        if (modelo != null) {
            modelo.setAtivo(ativo);
        } else {
            throw new NotFoundException("Modelo não encontrado");
        }
    }

    @Override
    public ModeloResponseDTO findById(long id) {
        Modelo modelo = modeloRepository.findById(id);
        if (modelo == null) throw new NotFoundException("Modelo não encontrado");
        return ModeloResponseDTO.valueOf(modelo);
    }

    // --- LISTAGEM DE ATIVOS ---
    @Override
    public List<ModeloResponseDTO> findAll(int page, int pageSize) {
        // Ajuste: Busca apenas onde ativo = true
        return modeloRepository.find("ativo = ?1", true)
                .page(page, pageSize)
                .list()
                .stream()
                .map(ModeloResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<ModeloResponseDTO> findByNome(String nome, int page, int pageSize) {
        // O repositório já foi ajustado para filtrar ativos no método findByNome
        PanacheQuery<Modelo> query = modeloRepository.findByNome(nome)
                .page(Page.of(page, pageSize));

        return query.list().stream()
                .map(ModeloResponseDTO::valueOf)
                .toList();
    }

    @Override
    public List<ModeloResponseDTO> findByMarca(Long idMarca) {
        // O repositório já foi ajustado para filtrar ativos
        List<Modelo> modelos = modeloRepository.findByMarca(idMarca);

        return modelos.stream()
                .map(ModeloResponseDTO::valueOf)
                .toList();
    }

    // --- LISTAGEM DA LIXEIRA (Inativos) ---
    // Adicione este método na interface ModeloService
    public List<ModeloResponseDTO> findInativos(int page, int pageSize) {
        return modeloRepository.findInativos() // Usa o método novo do Repo
                .page(page, pageSize)
                .list()
                .stream()
                .map(ModeloResponseDTO::valueOf)
                .toList();
    }

    // --- COUNTS ---
    @Override
    public long count() {
        // Conta apenas os ativos
        return modeloRepository.find("ativo = ?1", true).count();
    }

    @Override
    public long count(String nome) {
        // O findByNome do repo já filtra ativos
        return modeloRepository.findByNome(nome).count();
    }

    // Adicione na interface ModeloService
    public long countInativos() {
        return modeloRepository.findInativos().count();
    }

    @Override
    @Transactional
    public CaracteristicasResponseDTO caracteristicaForModelo(long idModelo, long idCaracteristica) {
        Modelo modelo = modeloRepository.findById(idModelo);
        if (modelo == null) throw new NotFoundException("Modelo não encontrado");

        CaracteristicasGerais caracteristicasGerais = caracteristicasGeraisRepository.findById(idCaracteristica);

        modelo.setCaracteristicas(caracteristicasGerais);

        return CaracteristicasResponseDTO.valueOf(caracteristicasGerais);
    }
}