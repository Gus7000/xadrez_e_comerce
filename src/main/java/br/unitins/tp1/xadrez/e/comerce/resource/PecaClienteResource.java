package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PecaClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.PecaMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import br.unitins.tp1.xadrez.e.comerce.service.PecaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cliente/peca")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PecaClienteResource {

    @Inject
    PecaService service;

    @GET
    public Response findAll() {
        List<Peca> pecas = service.findAll();
        List<PecaClienteResponseDTO> lista = pecas.stream().map(PecaMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/cor/{corId}")
    public Response findByCor(@PathParam("corId") Long corId) {
        List<PecaClienteResponseDTO> lista = service.findByCor(corId)
                .stream()
                .map(PecaMapper::toClienteResponseDTO)
                .toList();

        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/tipo/{tipoId}")
    public Response findByTipo(@PathParam("tipoId") Long tipoId) {
        List<PecaClienteResponseDTO> lista = service.findByTipo(tipoId)
                .stream()
                .map(PecaMapper::toClienteResponseDTO)
                .toList();

        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/material/{materialId}")
    public Response findByMaterial(@PathParam("materialId") Long materialId) {
        List<PecaClienteResponseDTO> lista = service.findByMaterial(materialId)
                .stream()
                .map(PecaMapper::toClienteResponseDTO)
                .toList();

        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Peca peca = service.findById(id);
        return Response.ok(PecaMapper.toClienteResponseDTO(peca)).build();
    }

}