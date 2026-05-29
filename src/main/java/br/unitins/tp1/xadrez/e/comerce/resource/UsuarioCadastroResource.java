package br.unitins.tp1.xadrez.e.comerce.resource;

import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioPerfilUpdateDTO;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/me")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("CLIENTE")
public class UsuarioCadastroResource {

    @Inject
    UsuarioService usuarioService;

    @PUT
    @Path("/perfil")
    @Transactional
    public Response atualizarPerfil(@Valid UsuarioPerfilUpdateDTO dto) {
        usuarioService.atualizarPerfil(dto);
        return Response.ok().build();
    }
}