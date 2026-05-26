package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.EnderecoMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Endereco;
import br.unitins.tp1.xadrez.e.comerce.service.EnderecoService;
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

@Path("/admin/endereco")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class EnderecoResource {

    @Inject
    EnderecoService service;

    @GET
    public Response findAll() {
        List<EnderecoResponseDTO> lista = service.findAll().stream().map(EnderecoMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Endereco endereco = service.findById(id);
        return Response.ok(EnderecoMapper.toResponseDTO(endereco)).build();
    }

    @GET
    @Path("/find/usuario/{usuarioId}")
    public Response findByUsuario(@PathParam("usuarioId") Long usuarioId) {
        List<EnderecoResponseDTO> lista = service.findByUsuarioId(usuarioId).stream().map(EnderecoMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }

    @POST
    @Transactional
    public Response create(@Valid EnderecoRequestDTO dto) {
        Endereco created = service.create(dto);
        return Response.status(201).entity(EnderecoMapper.toResponseDTO(created)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid EnderecoRequestDTO dto) {
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
