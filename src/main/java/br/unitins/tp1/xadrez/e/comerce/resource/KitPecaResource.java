package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.KitPecaRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.KitPecaResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.KitPecaMapper;
import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;
import br.unitins.tp1.xadrez.e.comerce.repository.PecaRepository;
import br.unitins.tp1.xadrez.e.comerce.service.KitPecaServiceImpl;
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

@Path("/kit-peca")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KitPecaResource {

    @Inject
    KitPecaServiceImpl service;

    @Inject
    PecaRepository pecaRepository;

    @GET
    public Response findAll() {
        List<KitPecaResponseDTO> lista = service.findAll().stream().map(KitPecaMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        KitPeca kitPeca = service.findById(id);
        return Response.ok(KitPecaMapper.toResponseDTO(kitPeca)).build();
    }

    @POST
    @Transactional
    public Response create(@Valid KitPecaRequestDTO dto) {
        KitPeca kitPeca = KitPecaMapper.toEntity(dto, pecaRepository);
        KitPeca criado = service.create(kitPeca);
        return Response.status(201).entity(KitPecaMapper.toResponseDTO(criado)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid KitPecaRequestDTO dto) {
        KitPeca kitPeca = KitPecaMapper.toEntity(dto, pecaRepository);
        service.update(id, kitPeca);
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
