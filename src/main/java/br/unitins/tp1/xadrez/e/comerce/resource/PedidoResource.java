package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoStatusUpdateDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.PedidoMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.service.PedidoService;
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

@Path("/admin/pedido")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class PedidoResource {

    @Inject
    PedidoService service;

    @GET
    public Response findAll() {
        List<PedidoResponseDTO> lista = service.findAll().stream().map(PedidoMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Pedido pedido = service.findById(id);
        return Response.ok(PedidoMapper.toResponseDTO(pedido)).build();
    }

    @GET
    @Path("/find/usuario/{usuarioId}")
    public Response findByUsuario(@PathParam("usuarioId") Long usuarioId) {
        List<PedidoResponseDTO> lista = service.findByUsuarioId(usuarioId).stream().map(PedidoMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @POST
    @Transactional
    public Response create(@Valid PedidoRequestDTO dto) {
        Pedido created = service.create(dto);
        return Response.status(201).entity(PedidoMapper.toResponseDTO(created)).build();
    }

    @PUT
    @Path("/{id}/status")
    @Transactional
    public Response updateStatus(@PathParam("id") Long id, @Valid PedidoStatusUpdateDTO dto) {
        service.updateStatus(id, dto.status());
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
