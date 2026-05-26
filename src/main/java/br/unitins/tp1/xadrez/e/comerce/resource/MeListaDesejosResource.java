package br.unitins.tp1.xadrez.e.comerce.resource;

import br.unitins.tp1.xadrez.e.comerce.DTO.MeListaDesejosItemRequestDTO;

import br.unitins.tp1.xadrez.e.comerce.mapper.ListaDesejosMapper;
import br.unitins.tp1.xadrez.e.comerce.model.ListaDesejos;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.ListaDesejosService;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import io.quarkus.security.identity.SecurityIdentity;

@Path("/me/lista-desejos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"CLIENTE","ADMIN"})
public class MeListaDesejosResource {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    UsuarioService usuarioService;

    @Inject
    ListaDesejosService listaDesejosService;

    @GET
    public Response getMyLista() {
        String login = securityIdentity.getPrincipal().getName();
        Usuario usuario = usuarioService.findByLogin(login);
        ListaDesejos lista = listaDesejosService.findOrCreateByUsuarioId(usuario.getId());
        return Response.ok(ListaDesejosMapper.toResponseDTO(lista)).build();
    }

    @POST
    @Path("/itens")
    @Transactional
    public Response addItem(@Valid MeListaDesejosItemRequestDTO dto) {
        String login = securityIdentity.getPrincipal().getName();
        Usuario usuario = usuarioService.findByLogin(login);
        listaDesejosService.addItem(usuario.getId(), dto.jogoId());
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/itens/{produtoId}")
    @Transactional
    public Response removeItem(@PathParam("produtoId") Long produtoId) {
        String login = securityIdentity.getPrincipal().getName();
        Usuario usuario = usuarioService.findByLogin(login);
        listaDesejosService.removeItem(usuario.getId(), produtoId);
        return Response.noContent().build();
    }
}
