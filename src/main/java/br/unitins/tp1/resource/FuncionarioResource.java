package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Funcionario.FuncionarioDeleteRequestDTO;
import br.unitins.tp1.model.DTO.Funcionario.FuncionarioRequestDTO;
import br.unitins.tp1.model.DTO.Funcionario.FuncionarioUpdateDadosDTO;
import br.unitins.tp1.model.DTO.Usuario.RedefinirSenhaRequestDTO;
import br.unitins.tp1.model.DTO.Usuario.UsuarioUpdateRequestDTO;
import br.unitins.tp1.service.Funcionario.FuncionarioServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.logging.Logger;

@Path("/funcionario")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    @Inject
    FuncionarioServiceImpl funcionarioService;

    private static final Logger logger = Logger.getLogger(FuncionarioResource.class.getName());

    @Inject
    JsonWebToken jwt;

    // =========================================================================
    // 1. CRIAÇÃO E ATUALIZAÇÃO DE DADOS BÁSICOS
    // =========================================================================

    @POST
    @RolesAllowed("adm")
    @Path("/incluir")
    public Response incluir(FuncionarioRequestDTO dto) {
        logger.info("Novo funcionário criado: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(funcionarioService.create(dto)).build();
    }

    @PUT
    @RolesAllowed("adm")
    @Path("/atualizar-dados-com-validacao")
    public Response atualizarDadosComValidacao(FuncionarioUpdateDadosDTO dto) {
        logger.info("Atualização de dados solicitada para funcionário ID: " + dto.idFuncionario());
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        funcionarioService.updateDadosComValidacao(dto);

        return Response.noContent().build();
    }

    // =========================================================================
    // 2. GERENCIAMENTO DE CREDENCIAIS (SENHA E USERNAME)
    // =========================================================================

    @PUT
    @RolesAllowed("adm")
    @Path("/{id}/atualizar-credenciais")
    public Response atualizarCredenciais(@PathParam("id") long id, UsuarioUpdateRequestDTO dto) {
        logger.info("Credenciais do funcionário com id: " + id + " solicitada atualização por ADM.");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        funcionarioService.updateCredenciaisFuncionario(id, dto);

        return Response.noContent().build();
    }

    @PUT
    @RolesAllowed("adm")
    @Path("/redefinir-senha")
    public Response redefinirSenha(RedefinirSenhaRequestDTO dto) {
        logger.info("Redefinição de senha solicitada para o funcionário: " + dto.username());
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);

        funcionarioService.redefinirSenhaFuncionario(dto);

        return Response.noContent().build();
    }

    // =========================================================================
    // 3. BUSCAS E DELEÇÃO
    // =========================================================================

    @DELETE
    @Path("/deletar")
    @RolesAllowed("adm")
    @Transactional
    public Response deletar(
            @HeaderParam("X-Password") String senha,
            FuncionarioDeleteRequestDTO dto
    ) {
        logger.info("Solicitação de exclusão para ID: " + dto.idFuncionario());
        funcionarioService.delete(dto.idFuncionario(), senha);
        return Response.noContent().build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{id}/procurar-por-id")
    public Response findById(@PathParam("id") Long id) {
        logger.info("Procurando funcionário com id: " + id);
        return Response.ok().entity(funcionarioService.findById(id)).build();
    }

    // --- MÉTODOS PAGINADOS ---

    @GET
    @RolesAllowed("adm")
    public Response procurarTodos(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize
    ) {
        logger.info("Procurando todos os funcionários");

        // Retorna a lista paginada E o total de registros no Header para o Angular calcular as páginas
        return Response.ok(funcionarioService.findAll(page, pageSize))
                .header("X-Total-Count", funcionarioService.count())
                .build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{nome}/procurar-por-nome")
    public Response procurarPorNome(
            @PathParam("nome") String nome,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize
    ) {
        logger.info("Procurando funcionário por nome: " + nome);

        return Response.ok(funcionarioService.findByNome(nome, page, pageSize))
                .header("X-Total-Count", funcionarioService.count(nome))
                .build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{username}/procurar-por-username")
    public Response procurarPorUsername(
            @PathParam("username") String username,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize
    ){
        logger.info("Procurando funcionário por username (parcial): " + username);

        // Usa o método sobrecarregado que criamos no Service para busca paginada e like
        return Response.ok(funcionarioService.findByUsername(username, page, pageSize))
                .header("X-Total-Count", funcionarioService.countByUsername(username))
                .build();
    }
}