package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.EstadoDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Estado;

import br.unitins.tp1.xadrez.e.comerce.service.EstadoServiceImpl;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/estados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @Inject
    EstadoServiceImpl service;

    @GET
    public List<Estado> buscarTodos() {
        return service.findAll();
    }

    @POST
    @Transactional
    public Estado incluir(EstadoDTO estado) {
        return service.create(estado);
    }

    @GET
    @Path("/{id}")
    public Estado encontrarPorId(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @GET
    @Path("/find/{nome}")
    public List<Estado> encontrarPorNome(@PathParam("nome") String nome) {
        return service.findByNome(nome);
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, EstadoDTO dto) {
        service.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.delete(id);
    }
}
