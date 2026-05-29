package br.unitins.tp1.xadrez.e.comerce.resource;

import br.unitins.tp1.xadrez.e.comerce.DTO.MeResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/me")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("CLIENTE")
public class MeResource {

    @Inject
    UsuarioService usuarioService;

    @GET
    public Response me() {
        MeResponseDTO response = usuarioService.obterMeuPerfil();
        return Response.ok(response).build();
    }
}
