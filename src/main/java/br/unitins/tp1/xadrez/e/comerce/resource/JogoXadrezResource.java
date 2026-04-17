package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.JogoXadrezMapper;
import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.repository.KitPecaRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.TabuleiroRepository;
import br.unitins.tp1.xadrez.e.comerce.service.JogoXadrezServiceImpl;
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

@Path("/jogo-xadrez")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JogoXadrezResource {

    @Inject
    JogoXadrezServiceImpl service;

    @Inject
    KitPecaRepository kitPecaRepository;

    @Inject
    TabuleiroRepository tabuleiroRepository;

    @GET
    public Response findAll() {
        List<JogoXadrezResponseDTO> lista = service.findAll().stream().map(JogoXadrezMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        JogoXadrez jogoXadrez = service.findById(id);
        return Response.ok(JogoXadrezMapper.toResponseDTO(jogoXadrez)).build();
    }

    @GET
    @Path("/find/kit-peca/{kitPecaId}")
    public Response findByKitPeca(@PathParam("kitPecaId") Long kitPecaId) {
        List<JogoXadrezResponseDTO> lista = service.findByKitPeca(kitPecaId).stream().map(JogoXadrezMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/find/tabuleiro/{tabuleiroId}")
    public Response findByTabuleiro(@PathParam("tabuleiroId") Long tabuleiroId) {
        List<JogoXadrezResponseDTO> lista = service.findByTabuleiro(tabuleiroId).stream().map(JogoXadrezMapper::toResponseDTO).toList();
        return Response.ok(lista).build();
    }

    @POST
    @Transactional
    public Response create(@Valid JogoXadrezRequestDTO dto) {
        var kitPeca = kitPecaRepository.findById(dto.kitPecaId());
        var tabuleiro = tabuleiroRepository.findById(dto.tabuleiroId());
        
        if (kitPeca == null) {
            throw new jakarta.ws.rs.NotFoundException("Kit de Peças não encontrado");
        }
        if (tabuleiro == null) {
            throw new jakarta.ws.rs.NotFoundException("Tabuleiro não encontrado");
        }
        
        JogoXadrez jogoXadrez = JogoXadrezMapper.toEntity(dto, kitPeca, tabuleiro);
        JogoXadrez criado = service.create(jogoXadrez);

        return Response.status(201).entity(JogoXadrezMapper.toResponseDTO(criado)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, @Valid JogoXadrezRequestDTO dto) {
        var kitPeca = kitPecaRepository.findById(dto.kitPecaId());
        var tabuleiro = tabuleiroRepository.findById(dto.tabuleiroId());
        
        if (kitPeca == null) {
            throw new jakarta.ws.rs.NotFoundException("Kit de Peças não encontrado");
        }
        if (tabuleiro == null) {
            throw new jakarta.ws.rs.NotFoundException("Tabuleiro não encontrado");
        }
        
        JogoXadrez jogoXadrez = JogoXadrezMapper.toEntity(dto, kitPeca, tabuleiro);
        service.update(id, jogoXadrez);
        
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
