package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PecaClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.PecaMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import br.unitins.tp1.xadrez.e.comerce.service.PecaService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/pecas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PecaClienteResource {

    @Inject
    PecaService service;

    @GET
    public Response findAll(@QueryParam("corId") Long corId,
                            @QueryParam("tipoId") Long tipoId,
                            @QueryParam("materialId") Long materialId) {
        List<Peca> pecas;

        if (corId != null) {
            pecas = service.findByCor(corId);
        } else if (tipoId != null) {
            pecas = service.findByTipo(tipoId);
        } else if (materialId != null) {
            pecas = service.findByMaterial(materialId);
        } else {
            pecas = service.findAll();
        }

        List<PecaClienteResponseDTO> lista = pecas.stream().map(PecaMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Peca peca = service.findById(id);
        return Response.ok(PecaMapper.toClienteResponseDTO(peca)).build();
    }

}