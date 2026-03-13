package br.unitins.tp1.xadrez.e.comerce.resource;


import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.EstadoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.EstadoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.EstadoMapper;
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
    public List<EstadoResponseDTO> buscarTodos() {
        /*List<EstadoResponseDTO> lista = new ArrayList<EstadoResponseDTO>();
        for (Estado estado : service.findAll()){
            lista.add(EstadoMapper.toResponseDTO(estado));
        }
        return lista;*/

        return service.findAll()
        .stream()
        .map(e-> EstadoMapper.toResponseDTO(e)).toList();
    }

    @POST
    @Transactional
    public EstadoResponseDTO incluir(EstadoRequestDTO dto) {
        Estado estado =service.create(EstadoMapper.toEntity(dto));
        return EstadoMapper.toResponseDTO(estado);
    }

    @GET
    @Path("/find/{nome}")
    public List<EstadoResponseDTO> buscarPeloNome(@PathParam("nome") String nome) {
        return service.findByNome(nome)
        .stream()
        .map(e-> EstadoMapper.toResponseDTO(e)).toList();
    }

    @GET
    @Path("/{id}")
    public EstadoResponseDTO buscarPeloId(@PathParam("nome") Long id) {
        return EstadoMapper.toResponseDTO(service.findById(id));
    }

    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, EstadoRequestDTO dto) {
        service.update(id, EstadoMapper.toEntity(dto));
    }

    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id) {
        service.delete(id);
    }
}
