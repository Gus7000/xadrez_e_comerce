package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.RelogioMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Relogio;
import br.unitins.tp1.xadrez.e.comerce.service.RelogioServiceImpl;
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

@Path("/relogio")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RelogioResource {
    @Inject
    RelogioServiceImpl service;

    @GET
    public List<RelogioResponseDTO> buscarTodos(){
        return service.findAll()
        .stream()
        .map(r -> RelogioMapper.toResponseDTO(r)).toList();
    }

    @POST
    @Transactional
    public RelogioResponseDTO incluir(RelogioRequestDTO dto){
        Relogio relogio = service.create(RelogioMapper.toEntity(dto));
        return RelogioMapper.toResponseDTO(relogio);
    }

    @GET
    @Path("/find/{marca}")
    public List<RelogioResponseDTO> buscarPelaMarca(@PathParam("marca") String marca){
        return service.findByMarca(marca).stream().map(r -> RelogioMapper.toResponseDTO(r)).toList();
    }
    @GET
    @Path("/find/{tipo}")
    public List<RelogioResponseDTO> buscarPeloTipo(@PathParam("tipo") Long idTipo){
        return service.findByTipo(idTipo).stream().map(r -> RelogioMapper.toResponseDTO(r)).toList();
    }

    @GET
    @Path("/{id}")
    public RelogioResponseDTO buscarPeloId(@PathParam("id") Long id){
        return  RelogioMapper.toResponseDTO(service.findById(id));
    }
    @PUT
    @Path("/{id}")
    public void atualizar(@PathParam("id") Long id, RelogioRequestDTO dto){
        service.update(id, RelogioMapper.toEntity(dto));
    }
    @DELETE
    @Path("/{id}")
    public void deletar(@PathParam("id") Long id){
        service.delete(id);
    }
}
