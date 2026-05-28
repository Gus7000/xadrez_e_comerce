package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.TabuleiroMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;
import br.unitins.tp1.xadrez.e.comerce.service.TabuleiroService;
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

@Path("/admin/tabuleiros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class TabuleiroResource {

    @Inject
    TabuleiroService service;

    @GET
    public Response findAll(@QueryParam("tamanho") String tamanho, @QueryParam("materialId") Long materialId) {
        List<Tabuleiro> tabuleiros;

        if (tamanho != null && !tamanho.isBlank()) {
            tabuleiros = service.findByTamanho(tamanho);
        } else if (materialId != null) {
            tabuleiros = service.findByMaterial(materialId);
        } else {
            tabuleiros = service.findAll();
        }

        List<TabuleiroResponseDTO> lista = tabuleiros.stream().map(TabuleiroMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Tabuleiro tabuleiro = service.findById(id);
        return Response.ok(TabuleiroMapper.toResponseDTO(tabuleiro)).build();
    }

    @POST
    @Transactional
    @RolesAllowed("ADMIN")
    public Response create(@Valid TabuleiroRequestDTO dto) {
        Tabuleiro criado = service.create(dto);
        return Response.status(201).entity(TabuleiroMapper.toResponseDTO(criado)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public Response update(@PathParam("id") Long id, @Valid TabuleiroRequestDTO dto) {
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
