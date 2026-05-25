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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cliente/jogo-xadrez")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JogoXadrezClienteResource {

    @Inject
    JogoXadrezService service;

    @GET
    public Response findAll() {
        List<JogoXadrezClienteResponseDTO> lista = service.findAll().stream().map(JogoXadrezMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        JogoXadrez jogoXadrez = service.findById(id);
        return Response.ok(JogoXadrezMapper.toClienteResponseDTO(jogoXadrez)).build();
    }

    @GET
    @Path("/find/kit-peca/{kitPecaId}")
    public Response findByKitPeca(@PathParam("kitPecaId") Long kitPecaId) {
        List<JogoXadrezClienteResponseDTO> lista = service.findByKitPeca(kitPecaId).stream().map(JogoXadrezMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/tabuleiro/{tabuleiroId}")
    public Response findByTabuleiro(@PathParam("tabuleiroId") Long tabuleiroId) {
        List<JogoXadrezClienteResponseDTO> lista = service.findByTabuleiro(tabuleiroId).stream().map(JogoXadrezMapper::toClienteResponseDTO).toList();
        return Response.ok(lista).build();
    }
}