package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.JogoXadrezMapper;
import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.service.JogoXadrezService;
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

@Path("/admin/jogo-xadrez")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class JogoXadrezResource {

    @Inject
    JogoXadrezService service;

    @GET
    public Response findAll() {
        List<JogoXadrez> jogos = service.findAll();
        List<JogoXadrezResponseDTO> lista = jogos.stream().map(JogoXadrezMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/kit-peca/{kitPecaId}")
    public Response findByKitPeca(@PathParam("kitPecaId") Long kitPecaId) {
        List<JogoXadrezResponseDTO> lista = service.findByKitPeca(kitPecaId)
                .stream()
                .map(JogoXadrezMapper::toResponseDTO)
                .toList();

        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/tabuleiro/{tabuleiroId}")
    public Response findByTabuleiro(@PathParam("tabuleiroId") Long tabuleiroId) {
        List<JogoXadrezResponseDTO> lista = service.findByTabuleiro(tabuleiroId)
                .stream()
                .map(JogoXadrezMapper::toResponseDTO)
                .toList();

        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        JogoXadrez jogoXadrez = service.findById(id);
        return Response.ok(JogoXadrezMapper.toResponseDTO(jogoXadrez)).build();
    }

    @POST
    @Transactional
    @RolesAllowed("ADMIN")
    public Response create(@Valid JogoXadrezRequestDTO dto) {
        JogoXadrez criado = service.create(dto);

        return Response.status(201).entity(JogoXadrezMapper.toResponseDTO(criado)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public Response update(@PathParam("id") Long id, @Valid JogoXadrezRequestDTO dto) {
        service.update(id, dto);
        
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("ADMIN")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
