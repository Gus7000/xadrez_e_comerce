package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PecaRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.PecaResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.PecaMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import br.unitins.tp1.xadrez.e.comerce.service.PecaService;
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
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin/pecas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class PecaResource {

    @Inject
    PecaService service;

    @GET
    public Response findAll(@QueryParam("corId") Long corId,
                            @QueryParam("tipoId") Long tipoId,
                            @QueryParam("materialId") Long materialId) {
        List<Peca> pecas;

        if (corId != null) {
            pecas = service.findByCor(corId);
        } else if (tipoId != null) {
            pecas = service.findByTipo(tipoId);
        } else if (materialId != null) {
            pecas = service.findByMaterial(materialId);
        } else {
            pecas = service.findAll();
        }

        List<PecaResponseDTO> lista = pecas.stream().map(PecaMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Peca peca = service.findById(id);
        return Response.ok(PecaMapper.toResponseDTO(peca)).build();
    }

    @POST
    @Transactional
    public Response create(@Valid PecaRequestDTO dto) {
        Peca criada = service.create(dto);
        return Response.status(201).entity(PecaMapper.toResponseDTO(criada)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid PecaRequestDTO dto) {
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
}
