package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.FabricanteRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.FabricanteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.FabricanteMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.service.FabricanteServiceImpl;
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

@Path("/fabricante")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResource {

    @Inject
    FabricanteServiceImpl service;

    @GET
    public Response findAll() {
        List<FabricanteResponseDTO> lista = service.findAll().stream().map(FabricanteMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Fabricante fabricante = service.findById(id);
        return Response.ok(FabricanteMapper.toResponseDTO(fabricante)).build();
    }

    @GET
    @Path("/find/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        List<FabricanteResponseDTO> lista =service.findByNome(nome).stream().map(FabricanteMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/cnpj/{cnpj}")
    public Response findByCnpj(@PathParam("cnpj") String cnpj) {
        Fabricante fabricante = service.findByCnpj(cnpj);
        return Response.ok(FabricanteMapper.toResponseDTO(fabricante)).build();
    }

    @POST
    @Transactional
    public Response create(@Valid FabricanteRequestDTO dto) {
        Fabricante fabricante = FabricanteMapper.toEntity(dto);
        Fabricante created = service.create(fabricante);
        return Response.status(201).entity(FabricanteMapper.toResponseDTO(created)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid FabricanteRequestDTO dto) {
        Fabricante fabricante = FabricanteMapper.toEntity(dto);
        service.update(id, fabricante);
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
