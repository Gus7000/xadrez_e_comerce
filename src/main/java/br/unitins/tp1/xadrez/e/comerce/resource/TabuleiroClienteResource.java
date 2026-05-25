package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.TabuleiroMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;
import br.unitins.tp1.xadrez.e.comerce.service.TabuleiroService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cliente/tabuleiro")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TabuleiroClienteResource {

    @Inject
    TabuleiroService service;

    @GET
    public Response findAll() {
        List<TabuleiroClienteResponseDTO> lista = service.findAll().stream().map(TabuleiroMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Tabuleiro tabuleiro = service.findById(id);
        return Response.ok(TabuleiroMapper.toClienteResponseDTO(tabuleiro)).build();
    }

    @GET
    @Path("/find/tamanho/{tamanho}")
    public Response findByTamanho(@PathParam("tamanho") String tamanho) {
        List<TabuleiroClienteResponseDTO> lista = service.findByTamanho(tamanho).stream().map(TabuleiroMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/material/{materialId}")
    public Response findByMaterial(@PathParam("materialId") Long materialId) {
        List<TabuleiroClienteResponseDTO> lista = service.findByMaterial(materialId).stream().map(TabuleiroMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }
}