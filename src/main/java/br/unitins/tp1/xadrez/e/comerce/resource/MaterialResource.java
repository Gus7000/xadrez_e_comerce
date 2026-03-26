package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.MaterialRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.MaterialResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.MaterialMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Material;
import br.unitins.tp1.xadrez.e.comerce.service.MaterialServiceImpl;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/material")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MaterialResource {

    @Inject
    MaterialServiceImpl service;

    @GET
    public List<MaterialResponseDTO> findAll() {
        return service.findAll().stream().map(MaterialMapper::toResponseDTO).toList();
    }

    @GET
    @Path("/{id}")
    public MaterialResponseDTO findById(@PathParam("id") Long id) {
        Material material = service.findById(id);
        return MaterialMapper.toResponseDTO(material);
    }

    @GET
    @Path("/find/tipo/{tipo}")
    public List<MaterialResponseDTO> findByTipo(@PathParam("tipo") String tipo) {
        return service.findByTipo(tipo).stream().map(MaterialMapper::toResponseDTO).toList();
    }

    @POST
    @Transactional
    public MaterialResponseDTO create(MaterialRequestDTO dto) {
        Material material = MaterialMapper.toEntity(dto);
        Material created = service.create(material);
        return MaterialMapper.toResponseDTO(created);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public void update(@PathParam("id") Long id, MaterialRequestDTO dto) {
        Material material = MaterialMapper.toEntity(dto);
        service.update(id, material);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }
}
