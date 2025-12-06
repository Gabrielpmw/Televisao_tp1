package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Televisao.TelevisaoPaginadaResponseDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoRequestDTO;
import br.unitins.tp1.model.DTO.Televisao.TelevisaoResponseDTO;
import br.unitins.tp1.model.Televisao.Televisao;
import br.unitins.tp1.service.Imagem.TelevisaoFileServiceImpl;
import br.unitins.tp1.service.Televisao.TelevisaoServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@Path("/televisoes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TelevisaoResource {

    @Inject
    TelevisaoServiceImpl televisaoService;

    @Inject
    JsonWebToken jwt;

    @Inject
    TelevisaoFileServiceImpl fileService;

    private static final Logger logger = Logger.getLogger(TelevisaoResource.class.getName());

    // =======================================
    // ============ CREATE ===================
    // =======================================
    @POST
    //@RolesAllowed("adm")
    public Response incluir(TelevisaoRequestDTO dto) {
        logger.info("Nova televisão criada: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED)
                .entity(televisaoService.create(dto))
                .build();
    }

    // =======================================
    // ============ UPDATE ===================
    // =======================================
    @PUT
    //@RolesAllowed("adm")
    @Path("/{id}/atualizar")
    public Response atualizar(@PathParam("id") long id, TelevisaoRequestDTO dto) {
        logger.info("Televisão com id: " + id + " atualizada para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        televisaoService.update(id, dto);
        return Response.noContent().build();
    }

    // =======================================
    // ======== SOFT DELETE (DESATIVAR) ======
    // =======================================
    @DELETE
    //@RolesAllowed("adm")
    @Path("/{id}/desativar") // CORRETO: Chama o soft delete no Service
    public Response apagar(@PathParam("id") long id) {
        logger.info("Televisão logicamente apagada (desativada) com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        televisaoService.delete(id);
        return Response.noContent().build();
    }

    // =======================================
    // =========== RESTAURAR (ATIVAR) ========
    // =======================================
    @PATCH
    //@RolesAllowed("adm")
    @Path("/{id}/restaurar") // CORRETO: Chama o método de restauração
    public Response restaurar(@PathParam("id") long id) {
        logger.info("Restaurando (ativando) televisão com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        televisaoService.alterarStatus(id, true);
        return Response.noContent().build();
    }

    // =======================================
    // =========== BUSCAR INATIVAS ===========
    // =======================================
    @GET
    //@RolesAllowed("adm")
    @Path("/inativas") // CORRETO: Rota para a lixeira
    public Response buscarInativas(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {

        logger.info("Buscando televisões inativas (lixeira)");

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        // Chamamos o novo método do Service
        List<TelevisaoResponseDTO> lista = televisaoService.findInativas(page, pageSize);
        long total = televisaoService.countInativas();

        return Response.ok(new TelevisaoPaginadaResponseDTO(lista, total)).build();
    }


    // =======================================
    // ============ FIND BY ID ===============
    // =======================================
    @GET
    //@RolesAllowed("adm")
    @Path("/{id}/buscar-por-id")
    public Response buscarPorId(@PathParam("id") long id) {
        logger.info("Buscando televisão com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        Televisao tv = televisaoService.findById(id);

        return Response.ok(TelevisaoResponseDTO.valueOf(tv)).build();
    }

    // =======================================
    // ======= LISTA DE MARCAS (NOVO) ========
    // =======================================
    @GET
    @Path("/marcas")
    public Response buscarMarcas() {
        return Response.ok(televisaoService.findAllMarcas()).build();
    }

    // =======================================
    // ======= FIND ALL COM FILTROS (ATIVOS) ==
    // =======================================
    @GET // CORRETO: É a rota principal para listar ativos e aplicar filtros
    public Response buscarTodos(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize,
            @QueryParam("marcas") List<String> marcas,
            @QueryParam("maxPolegada") Integer maxPolegada,
            @QueryParam("tipos") List<String> tipos,
            @QueryParam("sort") String sort
    ) {
        logger.info("Buscando televisões com filtros aplicados");

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        List<TelevisaoResponseDTO> lista = televisaoService.findByFiltros(marcas, maxPolegada, tipos, sort, page, pageSize);
        long total = televisaoService.countByFiltros(marcas, maxPolegada, tipos);

        return Response.ok(new TelevisaoPaginadaResponseDTO(lista, total)).build();
    }

    // =======================================
    // ======= FIND BY NOME DO MODELO ========
    // =======================================
    @GET
    //@RolesAllowed("adm")
    @Path("/modelo/{nomeModelo}")
    public Response buscarPorModelo(@PathParam("nomeModelo") String nomeModelo,
                                    @QueryParam("page") @DefaultValue("0") int page,
                                    @QueryParam("pageSize") @DefaultValue("10") int pageSize) {

        logger.info("Buscando televisões pelo modelo: " + nomeModelo);

        page = Math.max(0, page);
        pageSize = Math.min(Math.max(1, pageSize), 100);

        return Response.ok(televisaoService.findByModelo(nomeModelo, page, pageSize))
                .header("X-Page", page)
                .header("X-Page-Size", pageSize)
                .header("X-Total-Count", televisaoService.count(nomeModelo))
                .build();
    }

    // =======================================
    // ======= FIND MODELO POR TV ID =========
    // =======================================
    @GET
    //@RolesAllowed("adm")
    @Path("/{idTelevisao}/modelo")
    public Response buscarModeloDaTelevisao(@PathParam("idTelevisao") long idTelevisao) {
        logger.info("Buscando modelo da televisão com id: " + idTelevisao);
        return Response.ok(televisaoService.findTelevisaoByModelo(idTelevisao)).build();
    }

    // =======================================
    // =========== IMAGEM UPLOAD =============
    // =======================================
    @GET
    @Path("/imagem/download/{nomeImagem}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response download(@PathParam("nomeImagem") String nomeImagem) {
        Response.ResponseBuilder response = Response.ok(fileService.download(nomeImagem));
        response.header("Content-Disposition", "attachment;filename=" + nomeImagem);
        return response.build();
    }

    @PATCH
    @Path("/imagem/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response salvarImagem(
            @RestForm("idTelevisao")
            @NotNull(message = "idTelevisão é obrigatório.")
            @Min(value = 1, message = "idTelevisão deve ser maior ou igual a 1.")
            Long idTelevisao,

            @RestForm("file")
            @NotNull(message = "Arquivo de imagem é obrigatório.")
            FileUpload file) {

        try {
            fileService.salvar(idTelevisao, file);
            return Response.noContent().build();
        } catch (IOException e) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }
}