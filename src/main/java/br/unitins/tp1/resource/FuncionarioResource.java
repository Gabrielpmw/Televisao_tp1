package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Funcionario.FuncionarioDeleteRequestDTO;
import br.unitins.tp1.model.DTO.Funcionario.FuncionarioRequestDTO;
import br.unitins.tp1.model.DTO.Funcionario.FuncionarioUpdateDadosDTO; // NOVO DTO DE ATUALIZAÇÃO SEGURA
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

    private static final Logger logger = Logger.getLogger(FuncionarioResource.class.getName()); // Ajuste no Logger

    @Inject
    JsonWebToken jwt;

    // =========================================================================
    // 1. CRIAÇÃO E ATUALIZAÇÃO DE DADOS BÁSICOS
    // =========================================================================

    @POST
    @RolesAllowed("adm")
    @Path("/incluir") // Adicionado path para clareza
    public Response incluir(FuncionarioRequestDTO dto) {
        logger.info("Novo funcionário criado: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(funcionarioService.create(dto)).build();
    }

    @PUT
    @RolesAllowed("adm")
    @Path("/atualizar-dados-com-validacao")
    // Endpoint para ADM atualizar os dados do funcionário, exigindo a senha ATUAL do alvo.
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
    // Endpoint para ADM atualizar username e senha de um funcionário (forçar troca)
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
    // Endpoint para redefinição de senha (ADM pode fazer isso, ou em caso de esquecimento)
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
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(funcionarioService.findById(id)).build();
    }

    @GET
    @RolesAllowed("adm")
    public Response procurarTodos() {
        logger.info("Procurando todos os funcionários");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(funcionarioService.findAll()).build();
    }

    @GET
    @RolesAllowed("adm")
    @Path("/{username}/procurar-por-username")
    public Response procurarPorUsername(@PathParam("username") String username){
        logger.info("Procurando funcionário por username: " + username);
        String username1 = jwt.getSubject();
        logger.info("Usuário responsável: " + username1);
        return Response.ok().entity(funcionarioService.findByUsername(username)).build();
    }
}