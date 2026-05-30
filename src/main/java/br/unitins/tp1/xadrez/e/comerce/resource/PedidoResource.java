package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoStatusUpdateDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.PedidoMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.service.PedidoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class PedidoResource {

    @Inject
    PedidoService service;

    @GET
    public Response findAll(@QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size) {
        List<PedidoResponseDTO> lista = service.findAll(page, size).stream().map(PedidoMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Pedido pedido = service.findById(id);
        return Response.ok(PedidoMapper.toResponseDTO(pedido)).build();
    }

    @jakarta.ws.rs.PATCH
    @Path("/{id}/status")
    public Response updateStatus(@PathParam("id") Long id, PedidoStatusUpdateDTO dto) {
        service.updateStatus(id, dto.status());
        return Response.noContent().build();
    }
}
