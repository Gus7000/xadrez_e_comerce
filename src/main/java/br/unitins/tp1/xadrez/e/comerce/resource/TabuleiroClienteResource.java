package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.TabuleiroMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;
import br.unitins.tp1.xadrez.e.comerce.service.TabuleiroService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tabuleiros")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TabuleiroClienteResource {

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

        List<TabuleiroClienteResponseDTO> lista = tabuleiros.stream().map(TabuleiroMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Tabuleiro tabuleiro = service.findById(id);
        return Response.ok(TabuleiroMapper.toClienteResponseDTO(tabuleiro)).build();
    }

}