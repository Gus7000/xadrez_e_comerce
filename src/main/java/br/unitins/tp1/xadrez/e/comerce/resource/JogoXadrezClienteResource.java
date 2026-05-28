package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezClienteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.JogoXadrezMapper;
import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.service.JogoXadrezService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/jogos-xadrez")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JogoXadrezClienteResource {

    @Inject
    JogoXadrezService service;

    @GET
    public Response findAll(@QueryParam("kitPecaId") Long kitPecaId, @QueryParam("tabuleiroId") Long tabuleiroId) {
        List<JogoXadrez> jogos;

        if (kitPecaId != null) {
            jogos = service.findByKitPeca(kitPecaId);
        } else if (tabuleiroId != null) {
            jogos = service.findByTabuleiro(tabuleiroId);
        } else {
            jogos = service.findAll();
        }

        List<JogoXadrezClienteResponseDTO> lista = jogos.stream().map(JogoXadrezMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        JogoXadrez jogoXadrez = service.findById(id);
        return Response.ok(JogoXadrezMapper.toClienteResponseDTO(jogoXadrez)).build();
    }

}