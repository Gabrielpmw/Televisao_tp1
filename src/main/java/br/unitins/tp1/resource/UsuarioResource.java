package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.Usuario.*;
import br.unitins.tp1.model.DTO.Usuario.Admin.UsuarioCreateAdminDTO;
import br.unitins.tp1.model.DTO.Usuario.Admin.UsuarioUpdateCredenciaisAdminDTO;
import br.unitins.tp1.model.DTO.Usuario.Admin.UsuarioUpdateDadosAdminDTO;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.UsuarioRepository;
import br.unitins.tp1.service.Usersss.UsuarioService; // Alterado para Interface (boa prática) ou mantenha Impl se preferir
import br.unitins.tp1.service.Usersss.UsuarioServiceImpl;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    private static final Logger logger = Logger.getLogger(UsuarioResource.class.getName());
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(UsuarioResource.class);

    @Inject
    UsuarioServiceImpl usuarioService;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    JsonWebToken jwt;

    // =========================================================================
    // ENDPOINTS DE CLIENTE / PÚBLICOS (Mantidos Originais)
    // =========================================================================

    @POST
    public Response criarUsuario(UsuarioCreateRequestDTO dto){
        // logger.info("Criando novo usuário");
        // String username = jwt.getSubject();
        // logger.info("Usuário responsável: " + username);
        return Response.status(Response.Status.CREATED).entity(usuarioService.create(dto)).build();
    }

    @PATCH
    //@RolesAllowed({"cliente", "adm"})
    @Path("/atualizar")
    public Response atualizarusuario(UsuarioUpdateRequestDTO dto){
        logger.info("Usuário atualizado: " + dto.usernameAntigo());
        logger.info("Atualizado para: " + dto);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        usuarioService.update(dto);

        return Response.noContent().build();
    }

    @DELETE
    //@RolesAllowed({"cliente", "adm"})
    @Path("/{id}/deletar")
    public Response deletar(@PathParam("id") long id){
        String username1 = jwt.getSubject();
        logger.info("Usuário responsável: " + username1);
        usuarioService.delete(id);

        return Response.noContent().build();
    }

    @GET
    //@RolesAllowed({"adm"})
    @Path("/{id}/procurar-id")
    public Response procurarPorId(@PathParam("id") long id){
        logger.info("Buscando usuário com id: " + id);
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(usuarioService.findById(id)).build();
    }

    @GET
    //@RolesAllowed({"adm"})
    public Response buscarTodos(){
        logger.info("Buscando todos os usuários");
        String username = jwt.getSubject();
        logger.info("Usuário responsável: " + username);
        return Response.ok().entity(usuarioService.findAll()).build();
    }

    @GET
    //@RolesAllowed({"adm"})
    @Path("/{username}/buscar-username")
    public Response buscarPorUsername(@PathParam("username") String username){
        logger.info("Buscando usuário com username de:" + username);
        String username1 = jwt.getSubject();
        logger.info("Usuário responsável: " + username1);
        return Response.ok().entity(usuarioService.findByUsername(username)).build();
    }

    @PUT
    @Path("/recuperar-senha")
    public Response recuperarSenha(RedefinirSenhaRequestDTO dto) {
        logger.info("Tentativa de redefinição de senha para o usuário: " + dto.username());
        usuarioService.redefinirSenhaUsuario(dto);
        return Response.noContent().build();
    }

    @PATCH
    @Path("/dados-pessoais")
    @RolesAllowed({"cliente", "adm"})
    public Response atualizarDadosPessoais(DadosPessoaisRequestDTO dto) {
        String username = jwt.getSubject(); // Pega o login do token

        // Busca o usuário no banco
        Usuario usuarioLogado = usuarioRepository.findByUsername(username);

        // VERIFICAÇÃO DE SEGURANÇA (NOVO)
        if (usuarioLogado == null) {
            // Se o token é válido mas o usuário sumiu do banco
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuário não encontrado. Faça login novamente.")
                    .build();
        }

        // Se encontrou, prossegue com a atualização
        usuarioService.updateDadosPessoais(usuarioLogado.getId(), dto);

        return Response.noContent().build();
    }

    @PATCH
    @Path("/atualizar-credenciais")
    @RolesAllowed({"cliente", "adm"})
    public Response atualizarCredenciais(@Valid UpdateCredenciaisDTO dto) {
        String username = jwt.getSubject();
        Usuario usuarioLogado = usuarioRepository.findByUsername(username);

        if (usuarioLogado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        usuarioService.atualizarCredenciais(usuarioLogado.getId(), dto);

        return Response.noContent().build();
    }

    // =========================================================================
    // ENDPOINTS DE ADMINISTRADOR (Novos Métodos)
    // =========================================================================

    @POST
    @Path("/admin")
    @RolesAllowed("adm") // Apenas Admin pode criar usuários diretamente por aqui
    public Response createByAdmin(@Valid UsuarioCreateAdminDTO dto) {
        logger.info("Admin criando novo usuário: " + dto.username());
        return Response.status(Response.Status.CREATED)
                .entity(usuarioService.createByAdmin(dto))
                .build();
    }

    @PUT // PUT pois estamos substituindo o bloco de dados pessoais inteiro
    @Path("/admin/{id}/dados")
    @RolesAllowed("adm")
    public Response updateDadosByAdmin(@PathParam("id") Long id, @Valid UsuarioUpdateDadosAdminDTO dto) {
        logger.info("Admin atualizando dados do usuário ID: " + id);
        return Response.ok(usuarioService.updateDadosByAdmin(id, dto)).build();
    }

    @PATCH // PATCH pois é atualização pontual de credenciais
    @Path("/admin/{id}/credenciais")
    @RolesAllowed("adm")
    public Response updateCredenciaisByAdmin(@PathParam("id") Long id, @Valid UsuarioUpdateCredenciaisAdminDTO dto) {
        logger.info("Admin alterando credenciais do usuário ID: " + id);
        usuarioService.updateCredenciaisByAdmin(id, dto);
        return Response.noContent().build();
    }
}