package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PecaRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.PecaResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.PecaMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import br.unitins.tp1.xadrez.e.comerce.repository.MaterialRepository;
import br.unitins.tp1.xadrez.e.comerce.service.PecaServiceImpl;
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

@Path("/peca")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PecaResource {

    @Inject
    PecaServiceImpl service;

    @Inject
    MaterialRepository materialRepository;

    @GET
    public Response findAll() {
        List<PecaResponseDTO> lista = service.findAll().stream().map(PecaMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Peca peca = service.findById(id);
        return Response.ok(PecaMapper.toResponseDTO(peca)).build();
    }

    @GET
    @Path("/find/cor/{corId}")
    public Response findByCor(@PathParam("corId") Long corId) {
        List<PecaResponseDTO> lista = service.findByCor(corId).stream().map(PecaMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/tipo/{tipoId}")
    public Response findByTipo(@PathParam("tipoId") Long tipoId) {
        List<PecaResponseDTO> lista = service.findByTipo(tipoId).stream().map(PecaMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/material/{materialId}")
    public Response findByMaterial(@PathParam("materialId") Long materialId) {
        List<PecaResponseDTO> lista = service.findByMaterial(materialId).stream().map(PecaMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @POST
    @Transactional
    public Response create(@Valid PecaRequestDTO dto) {
        var material = materialRepository.findById(dto.materialId());
        Peca peca = PecaMapper.toEntity(dto, material);
        Peca criada = service.create(peca);
        
        return Response.status(201).entity(PecaMapper.toResponseDTO(criada)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid PecaRequestDTO dto) {
        var material = materialRepository.findById(dto.materialId());
        Peca peca = PecaMapper.toEntity(dto, material);
        service.update(id, peca);
        
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
