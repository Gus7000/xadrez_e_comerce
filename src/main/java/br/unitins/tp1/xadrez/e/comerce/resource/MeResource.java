package br.unitins.tp1.xadrez.e.comerce.resource;

import br.unitins.tp1.xadrez.e.comerce.DTO.MeResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.EnderecoMapper;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.ws.rs.Path;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.quarkus.security.identity.SecurityIdentity;

@Path("/me")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("CLIENTE")
public class MeResource {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    UsuarioService usuarioService;


    @GET
    public Response me() {
        Usuario usuario = usuarioService.obterOuCriarUsuarioLocal();
        List<EnderecoResponseDTO> enderecos = usuario.getEnderecos()
            .stream()
            .map(EnderecoMapper::toResponseDTO)
            .collect(Collectors.toList());

        MeResponseDTO response = new MeResponseDTO(
            usuario.getId(),
            usuario.getEmail(),
            usuario.getKeycloakId(),
            usuario.getNome(),
            usuario.getTelefone(),
            usuario.getCpf(),
            usuario.isCadastroCompleto(),
            enderecos,
            usuario.getDataCadastro());

        return Response.ok(response).build();
    }
}