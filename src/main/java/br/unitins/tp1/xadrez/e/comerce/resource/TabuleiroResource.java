package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.TabuleiroMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;
import br.unitins.tp1.xadrez.e.comerce.repository.FabricanteRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.MaterialRepository;
import br.unitins.tp1.xadrez.e.comerce.service.TabuleiroServiceImpl;
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

@Path("/tabuleiro")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TabuleiroResource {

    @Inject
    TabuleiroServiceImpl service;

    @Inject
    MaterialRepository materialRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    @GET
    public Response findAll() {
        List<TabuleiroResponseDTO> lista = service.findAll().stream().map(TabuleiroMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Tabuleiro tabuleiro = service.findById(id);
        return Response.ok(TabuleiroMapper.toResponseDTO(tabuleiro)).build();
    }

    @GET
    @Path("/find/tamanho/{tamanho}")
    public Response findByTamanho(@PathParam("tamanho") String tamanho) {
        List<TabuleiroResponseDTO> lista = service.findByTamanho(tamanho).stream().map(TabuleiroMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/material/{materialId}")
    public Response findByMaterial(@PathParam("materialId") Long materialId) {
        List<TabuleiroResponseDTO> lista = service.findByMaterial(materialId).stream().map(TabuleiroMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @POST
    @Transactional
    public Response create(@Valid TabuleiroRequestDTO dto) {
        var material = materialRepository.findById(dto.materialId());
        Tabuleiro tabuleiro = TabuleiroMapper.toEntity(dto, material, fabricanteRepository);
        Tabuleiro criado = service.create(tabuleiro);
        return Response.status(201).entity(TabuleiroMapper.toResponseDTO(criado)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid TabuleiroRequestDTO dto) {
        var material = materialRepository.findById(dto.materialId());
        Tabuleiro tabuleiro = TabuleiroMapper.toEntity(dto, material, fabricanteRepository);
        service.update(id, tabuleiro);
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
