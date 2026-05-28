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

@Path("/me/lista-desejos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"CLIENTE","ADMIN"})
public class MeListaDesejosResource {

    @Inject
    UsuarioService usuarioService;

    @Inject
    ListaDesejosService listaDesejosService;

    @GET
    public Response getMyLista() {
        Usuario usuario = usuarioService.obterOuCriarUsuarioLocal();
        ListaDesejos lista = listaDesejosService.findOrCreateByUsuarioId(usuario.getId());
        return Response.ok(ListaDesejosMapper.toResponseDTO(lista)).build();
    }

    @POST
    @Path("/itens")
    @Transactional
    public Response addItem(@Valid MeListaDesejosItemRequestDTO dto) {
        Usuario usuario = usuarioService.obterOuCriarUsuarioLocal();

        if (!usuario.isCadastroCompleto()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("É necessário completar o cadastro antes de adicionar itens à lista de desejos.")
                    .build();
        }

        listaDesejosService.addItem(usuario.getId(), dto.jogoId());
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/itens/{produtoId}")
    @Transactional
    public Response removeItem(@PathParam("produtoId") Long produtoId) {
        Usuario usuario = usuarioService.obterOuCriarUsuarioLocal();

        if (!usuario.isCadastroCompleto()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("É necessário completar o cadastro antes de remover itens da lista de desejos.")
                    .build();
        }

        listaDesejosService.removeItem(usuario.getId(), produtoId);
        return Response.noContent().build();
    }
}
