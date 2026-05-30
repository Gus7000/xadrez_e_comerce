package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.UsuarioMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.KeycloakAdminService;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import org.keycloak.representations.idm.UserRepresentation;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class UsuarioResource {

    @Inject
    UsuarioService service;

    @Inject
    KeycloakAdminService keycloakAdminService;

    @GET
    public Response findAll() {
        List<UsuarioResponseDTO> lista = keycloakAdminService.listarUsuarios().stream()
                .map(this::toAdminResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Usuario usuario = service.findById(id);
        return Response.ok(toAdminResponseDTO(usuario)).build();
    }

    @POST
    @Path("/{identificador}/promover")
    public Response promote(@PathParam("identificador") String identificador) {
        service.promoteToAdmin(identificador);
        return Response.noContent().build();
    }

    @POST
    @Path("/{identificador}/remover")
    public Response demote(@PathParam("identificador") String identificador) {
        service.demoteFromAdmin(identificador);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{identificador}")
    public Response delete(@PathParam("identificador") String identificador) {
        service.delete(identificador);
        return Response.noContent().build();
    }

    private UsuarioResponseDTO toAdminResponseDTO(Usuario usuario) {
        UserRepresentation keycloakUser = keycloakAdminService.obterUsuario(usuario.getKeycloakId());

        if (keycloakUser == null) {
            return UsuarioMapper.toResponseDTO(usuario);
        }

        return new UsuarioResponseDTO(
                usuario.getId(),
                keycloakUser.getEmail() != null ? keycloakUser.getEmail() : usuario.getEmail(),
                keycloakUser.getFirstName() != null ? keycloakUser.getFirstName() : usuario.getNome(),
                usuario.getKeycloakId(),
                usuario.getDataCadastro());
    }

    private UsuarioResponseDTO toAdminResponseDTO(UserRepresentation keycloakUser) {
        Usuario usuarioLocal = service.localizarOuCriarPorKeycloak(keycloakUser);

        if (usuarioLocal == null) {
            return new UsuarioResponseDTO(
                    null,
                    keycloakUser.getEmail(),
                    keycloakUser.getFirstName(),
                    keycloakUser.getId(),
                    null);
        }

        return new UsuarioResponseDTO(
                usuarioLocal.getId(),
                keycloakUser.getEmail() != null ? keycloakUser.getEmail() : usuarioLocal.getEmail(),
                keycloakUser.getFirstName() != null ? keycloakUser.getFirstName() : usuarioLocal.getNome(),
                usuarioLocal.getKeycloakId(),
                usuarioLocal.getDataCadastro());
    }
}
