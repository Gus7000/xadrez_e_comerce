package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.KitPecaClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.KitPecaMapper;
import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;
import br.unitins.tp1.xadrez.e.comerce.service.KitPecaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cliente/kit-peca")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class KitPecaClienteResource {

    @Inject
    KitPecaService service;

    @GET
    public Response findAll() {
        List<KitPecaClienteResponseDTO> lista = service.findAll().stream().map(KitPecaMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        KitPeca kitPeca = service.findById(id);
        return Response.ok(KitPecaMapper.toClienteResponseDTO(kitPeca)).build();
    }
}