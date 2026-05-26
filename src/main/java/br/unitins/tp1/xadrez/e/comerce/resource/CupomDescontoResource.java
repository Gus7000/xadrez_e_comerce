package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.CupomDescontoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.CupomDescontoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.CupomDescontoMapper;
import br.unitins.tp1.xadrez.e.comerce.model.CupomDesconto;
import br.unitins.tp1.xadrez.e.comerce.service.CupomDescontoService;
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

@Path("/admin/cupom")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class CupomDescontoResource {

    @Inject
    CupomDescontoService service;

    @GET
    public Response findAll() {
        List<CupomDescontoResponseDTO> lista = service.findAll().stream().map(CupomDescontoMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        CupomDesconto c = service.findById(id);
        return Response.ok(CupomDescontoMapper.toResponseDTO(c)).build();
    }

    @POST
    @Transactional
    public Response create(@Valid CupomDescontoRequestDTO dto) {
        CupomDesconto entidade = CupomDescontoMapper.toEntity(dto);
        CupomDesconto created = service.create(entidade);
        return Response.status(201).entity(CupomDescontoMapper.toResponseDTO(created)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid CupomDescontoRequestDTO dto) {
        CupomDesconto entidade = CupomDescontoMapper.toEntity(dto);
        service.update(id, entidade);
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
