package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.RelogioMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Relogio;
import br.unitins.tp1.xadrez.e.comerce.service.RelogioService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cliente/relogio")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RelogioClienteResource {

    @Inject
    RelogioService service;

    @GET
    public Response findAll() {
        List<RelogioClienteResponseDTO> lista = service.findAll().stream().map(RelogioMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/marca/{marca}")
    public Response findByMarca(@PathParam("marca") String marca) {
        List<RelogioClienteResponseDTO> lista = service.findByMarca(marca).stream().map(RelogioMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/tipo/{tipo}")
    public Response findByTipo(@PathParam("tipo") Long tipo) {
        List<RelogioClienteResponseDTO> lista = service.findByTipo(tipo).stream().map(RelogioMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Relogio relogio = service.findById(id);
        return Response.ok(RelogioMapper.toClienteResponseDTO(relogio)).build();
    }
}