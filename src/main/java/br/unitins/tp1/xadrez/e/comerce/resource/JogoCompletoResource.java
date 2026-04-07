package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.JogoCompletoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.JogoCompletoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.JogoCompletoMapper;
import br.unitins.tp1.xadrez.e.comerce.model.JogoCompleto;
import br.unitins.tp1.xadrez.e.comerce.repository.FabricanteRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.KitPecaRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.TabuleiroRepository;
import br.unitins.tp1.xadrez.e.comerce.service.JogoCompletoServiceImpl;
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

@Path("/jogo-completo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JogoCompletoResource {

    @Inject
    JogoCompletoServiceImpl service;

    @Inject
    KitPecaRepository kitPecaRepository;

    @Inject
    TabuleiroRepository tabuleiroRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    @GET
    public Response findAll() {
        List<JogoCompletoResponseDTO> lista = service.findAll().stream().map(JogoCompletoMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        JogoCompleto jogoCompleto = service.findById(id);
        return Response.ok(JogoCompletoMapper.toResponseDTO(jogoCompleto)).build();
    }

    @GET
    @Path("/find/kit-peca/{kitPecaId}")
    public Response findByKitPeca(@PathParam("kitPecaId") Long kitPecaId) {
        List<JogoCompletoResponseDTO> lista = service.findByKitPeca(kitPecaId).stream().map(JogoCompletoMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/tabuleiro/{tabuleiroId}")
    public Response findByTabuleiro(@PathParam("tabuleiroId") Long tabuleiroId) {
        List<JogoCompletoResponseDTO> lista = service.findByTabuleiro(tabuleiroId).stream().map(JogoCompletoMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @POST
    @Transactional
    public Response create(@Valid JogoCompletoRequestDTO dto) {
        var kitPeca = kitPecaRepository.findById(dto.kitPecaId());
        var tabuleiro = tabuleiroRepository.findById(dto.tabuleiroId());
        var fabricante = fabricanteRepository.findById(dto.fabricanteId());
        
        JogoCompleto jogoCompleto = JogoCompletoMapper.toEntity(dto, kitPeca, tabuleiro, fabricante);
        JogoCompleto criado = service.create(jogoCompleto);
        
        return Response.status(201).entity(JogoCompletoMapper.toResponseDTO(criado)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid JogoCompletoRequestDTO dto) {
        var kitPeca = kitPecaRepository.findById(dto.kitPecaId());
        var tabuleiro = tabuleiroRepository.findById(dto.tabuleiroId());
        var fabricante = fabricanteRepository.findById(dto.fabricanteId());
        
        JogoCompleto jogoCompleto = JogoCompletoMapper.toEntity(dto, kitPeca, tabuleiro, fabricante);
        service.update(id, jogoCompleto);
        
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
