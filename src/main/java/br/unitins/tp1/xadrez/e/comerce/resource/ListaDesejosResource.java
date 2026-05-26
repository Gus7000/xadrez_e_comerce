package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;


import br.unitins.tp1.xadrez.e.comerce.DTO.ListaDesejosResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.ListaDesejosMapper;
import br.unitins.tp1.xadrez.e.comerce.model.ListaDesejos;
import br.unitins.tp1.xadrez.e.comerce.service.ListaDesejosService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;


import jakarta.ws.rs.Consumes;

import jakarta.ws.rs.GET;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin/lista-desejos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class ListaDesejosResource {

    @Inject
    ListaDesejosService service;

    @GET
    public Response findAll() {
        List<ListaDesejosResponseDTO> lista = service.findAll().stream().map(ListaDesejosMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        ListaDesejos listaDesejos = service.findById(id);
        return Response.ok(ListaDesejosMapper.toResponseDTO(listaDesejos)).build();
    }

    @GET
    @Path("/find/usuario/{usuarioId}")
    public Response findByUsuario(@PathParam("usuarioId") Long usuarioId) {
        List<ListaDesejosResponseDTO> lista = service.findByUsuarioId(usuarioId).stream().map(ListaDesejosMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }
    // Admin write operations removed: administrative write endpoints for
    // user wishlists should only exist if there's a specific need (reports,
    // maintenance). User write operations are provided under `/me/lista-desejos`.
}
