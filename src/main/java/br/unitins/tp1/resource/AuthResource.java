package br.unitins.tp1.resource;

import br.unitins.tp1.model.DTO.AuthRequestDTO;
import br.unitins.tp1.model.DTO.Usuario.UsuarioResponseDTO;
import br.unitins.tp1.model.Funcionario;
import br.unitins.tp1.model.Perfil;
import br.unitins.tp1.model.Usuario;
import br.unitins.tp1.repository.FuncionarioRepository;
import br.unitins.tp1.repository.UsuarioRepository;
import br.unitins.tp1.service.Auth.HashService;
import br.unitins.tp1.service.Auth.JwtService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

import static jakarta.ws.rs.core.Response.status;

@Path("auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AuthResource.class);
    @Inject
    HashService hashService;

    @Inject
    JwtService jwtService;

    @Inject
    FuncionarioRepository funcionarioRepository;
    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    JsonWebToken jwt;

    private static final Logger logger = Logger.getLogger(AuthResource.class.getName());

    @POST
    //@Produces(MediaType.TEXT_PLAIN)
    public Response login(AuthRequestDTO dto) throws Exception {

        String hash = null;

        try {
            hash = hashService.getHashSenha(dto.senha());
        } catch (Exception e) {
            return status(Status.INTERNAL_SERVER_ERROR).build();
        }

        Usuario usuario = usuarioRepository.findByUsernameAndSenha(dto.username(), hash);

        if (usuario == null) {
            logger.warning("Usuário não encontrado: " + dto.username());

            return Response.status(Response.Status.FORBIDDEN)
                    .entity("Cliente não encontrado").build();
        }

        UsuarioResponseDTO usuarioResponseDTO = UsuarioResponseDTO.valueOf(usuario);

        logger.info("Login efetuado por: " + dto.username());

        String token = jwtService.generateJwt(usuarioResponseDTO.username(), usuarioResponseDTO.perfil().getNOME());

        return Response.ok(usuarioResponseDTO)
                .header("Authorization", token)
                .build();
    }
}
