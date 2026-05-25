package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.RelogioMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Relogio;
import br.unitins.tp1.xadrez.e.comerce.service.RelogioService;
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

@Path("/admin/relogio")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class RelogioResource {

    @Inject
    RelogioService service;

    @GET
    public Response findAll() {
        List<RelogioResponseDTO> lista = service.findAll()
                .stream()
                .map(RelogioMapper::toResponseDTO)
                .toList();

        return Response.ok(lista).build();
    }

    @POST
    @Transactional
    @RolesAllowed("ADMIN")
    public Response create(@Valid RelogioRequestDTO dto) {
        Relogio relogio = service.create(dto);

        return Response.status(201)
                .entity(RelogioMapper.toResponseDTO(relogio))
                .build();
    }

    @GET
    @Path("/find/marca/{marca}")
    public Response findByMarca(@PathParam("marca") String marca) {
        List<RelogioResponseDTO> lista = service.findByMarca(marca)
                .stream()
                .map(RelogioMapper::toResponseDTO)
                .toList();

        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/tipo/{tipo}")
    public Response findByTipo(@PathParam("tipo") Long tipo) {
        List<RelogioResponseDTO> lista = service.findByTipo(tipo)
                .stream()
                .map(RelogioMapper::toResponseDTO)
                .toList();

        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Relogio relogio = service.findById(id);

        return Response.ok(RelogioMapper.toResponseDTO(relogio)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public Response update(@PathParam("id") Long id, @Valid RelogioRequestDTO dto) {
        service.update(id, dto);

        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);

        return Response.noContent().build();
    }
}

