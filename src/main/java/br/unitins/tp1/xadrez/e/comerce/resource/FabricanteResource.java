package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.FabricanteRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.FabricanteResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.FabricanteMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.service.FabricanteServiceImpl;
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

@Path("/fabricante")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FabricanteResource {

    @Inject
    FabricanteServiceImpl service;

    @GET
    public List<FabricanteResponseDTO> findAll() {
        return service.findAll().stream().map(FabricanteMapper::toResponseDTO).toList();
    }

    @GET
    @Path("/{id}")
    public FabricanteResponseDTO findById(@PathParam("id") Long id) {
        Fabricante fabricante = service.findById(id);
        return FabricanteMapper.toResponseDTO(fabricante);
    }

    @GET
    @Path("/find/nome/{nome}")
    public List<FabricanteResponseDTO> findByNome(@PathParam("nome") String nome) {
        return service.findByNome(nome).stream().map(FabricanteMapper::toResponseDTO).toList();
    }

    @GET
    @Path("/find/cnpj/{cnpj}")
    public FabricanteResponseDTO findByCnpj(@PathParam("cnpj") String cnpj) {
        Fabricante fabricante = service.findByCnpj(cnpj);
        return FabricanteMapper.toResponseDTO(fabricante);
    }

    @POST
    @Transactional
    public FabricanteResponseDTO create(FabricanteRequestDTO dto) {
        Fabricante fabricante = FabricanteMapper.toEntity(dto);
        Fabricante created = service.create(fabricante);
        return FabricanteMapper.toResponseDTO(created);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public void update(@PathParam("id") Long id, FabricanteRequestDTO dto) {
        Fabricante fabricante = FabricanteMapper.toEntity(dto);
        service.update(id, fabricante);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        service.delete(id);
    }
}
