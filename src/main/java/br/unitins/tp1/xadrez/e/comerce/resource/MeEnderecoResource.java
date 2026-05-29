package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.EnderecoMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Endereco;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.EnderecoService;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.quarkus.security.identity.SecurityIdentity;

@Path("/me/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("CLIENTE")
public class MeEnderecoResource {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    UsuarioService usuarioService;

    @Inject
    EnderecoService enderecoService;

    private Usuario currentUsuario() {
        return usuarioService.obterOuCriarUsuarioLocal();
    }

    private void validarPertenceAoUsuario(Endereco endereco, Long usuarioId) {
        if (endereco == null || endereco.getUsuario() == null || !endereco.getUsuario().getId().equals(usuarioId)) {
            throw new jakarta.ws.rs.NotFoundException("Endereço não encontrado");
        }
    }

    @GET
    public Response findMine() {
        Usuario usuario = currentUsuario();
        List<EnderecoResponseDTO> lista = enderecoService.findByUsuarioId(usuario.getId()).stream().map(EnderecoMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @POST
    @Transactional
    public Response create(@Valid EnderecoRequestDTO dto) {
        Usuario usuario = currentUsuario();
        Endereco created = enderecoService.create(usuario.getId(), dto);
        return Response.status(201).entity(EnderecoMapper.toResponseDTO(created)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid EnderecoRequestDTO dto) {
        Usuario usuario = currentUsuario();
        validarPertenceAoUsuario(enderecoService.findById(id), usuario.getId());
        enderecoService.update(id, usuario.getId(), dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Usuario usuario = currentUsuario();
        validarPertenceAoUsuario(enderecoService.findById(id), usuario.getId());
        enderecoService.delete(id);
        return Response.noContent().build();
    }
}