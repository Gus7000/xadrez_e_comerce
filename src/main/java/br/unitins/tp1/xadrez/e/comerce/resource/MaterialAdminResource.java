package br.unitins.tp1.xadrez.e.comerce.resource;

import br.unitins.tp1.xadrez.e.comerce.DTO.MaterialRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.MaterialAdminResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.MaterialMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Material;
import br.unitins.tp1.xadrez.e.comerce.service.MaterialService;
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

import java.util.List;

@Path("/admin/material")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class MaterialAdminResource {

    @Inject
    MaterialService service;

    @GET
    public Response findAll() {
        List<Material> materiais = service.findAll();
        List<MaterialAdminResponseDTO> lista = materiais.stream().map(MaterialMapper::toAdminResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/tipo/{tipo}")
    public Response findByTipo(@PathParam("tipo") String tipo) {
        List<MaterialAdminResponseDTO> lista = service.findByTipo(tipo)
                .stream()
                .map(MaterialMapper::toAdminResponseDTO)
                .toList();

        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Material material = service.findById(id);
        return Response.ok(MaterialMapper.toAdminResponseDTO(material)).build();
    }

    @POST
    @Transactional
    public Response create(@Valid MaterialRequestDTO dto) {
        Material material = MaterialMapper.toEntity(dto);
        Material created = service.create(material);
        return Response.status(201)
                .entity(MaterialMapper.toAdminResponseDTO(created))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid MaterialRequestDTO dto) {
        Material material = MaterialMapper.toEntity(dto);
        service.update(id, material);
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
