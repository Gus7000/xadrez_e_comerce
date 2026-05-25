package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.FabricanteClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.FabricanteMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.service.FabricanteService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cliente/fabricante")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteClienteResource {

    @Inject
    FabricanteService service;

    @GET
    public Response findAll() {
        List<FabricanteClienteResponseDTO> lista = service.findAll().stream().map(FabricanteMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Fabricante fabricante = service.findById(id);
        return Response.ok(FabricanteMapper.toClienteResponseDTO(fabricante)).build();
    }

    @GET
    @Path("/find/nome/{nome}")
    public Response findByNome(@PathParam("nome") String nome) {
        List<FabricanteClienteResponseDTO> lista = service.findByNome(nome).stream().map(FabricanteMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }
}