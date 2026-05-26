package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PagamentoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.PagamentoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.PagamentoMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Pagamento;
import br.unitins.tp1.xadrez.e.comerce.service.PagamentoService;
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

@Path("/admin/pagamento")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class PagamentoResource {

    @Inject
    PagamentoService service;

    @GET
    public Response findAll() {
        List<PagamentoResponseDTO> lista = service.findAll().stream().map(PagamentoMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Pagamento pagamento = service.findById(id);
        return Response.ok(PagamentoMapper.toResponseDTO(pagamento)).build();
    }

    @GET
    @Path("/find/pedido/{pedidoId}")
    public Response findByPedido(@PathParam("pedidoId") Long pedidoId) {
        Pagamento pagamento = service.findByPedidoId(pedidoId);
        return Response.ok(PagamentoMapper.toResponseDTO(pagamento)).build();
    }

    @POST
    @Transactional
    public Response create(@Valid PagamentoRequestDTO dto) {
        Pagamento created = service.create(dto);
        return Response.status(201).entity(PagamentoMapper.toResponseDTO(created)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid PagamentoRequestDTO dto) {
        service.update(id, dto);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/{id}/estorno")
    @Transactional
    public Response estornar(@PathParam("id") Long id) {
        service.estornar(id);
        return Response.noContent().build();
    }
}
