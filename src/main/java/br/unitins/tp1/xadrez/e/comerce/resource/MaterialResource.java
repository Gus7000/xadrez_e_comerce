package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;


import br.unitins.tp1.xadrez.e.comerce.DTO.MaterialResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.MaterialMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Material;
import br.unitins.tp1.xadrez.e.comerce.service.MaterialService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/material")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MaterialResource {

    @Inject
    MaterialService service;

    @GET
    public Response findAll() {
        List<Material> materiais = service.findAll();
        List<MaterialResponseDTO> lista = materiais.stream().map(MaterialMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/tipo/{tipo}")
    public Response findByTipo(@PathParam("tipo") String tipo) {
        List<MaterialResponseDTO> lista = service.findByTipo(tipo)
                .stream()
                .map(MaterialMapper::toResponseDTO)
                .toList();

        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Material material = service.findById(id);
        return Response.ok(MaterialMapper.toResponseDTO(material)).build();
    }

}
