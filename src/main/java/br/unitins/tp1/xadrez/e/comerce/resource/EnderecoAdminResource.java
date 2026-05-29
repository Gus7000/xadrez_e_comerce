package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.EnderecoMapper;
import br.unitins.tp1.xadrez.e.comerce.service.EnderecoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class EnderecoAdminResource {

    @Inject
    EnderecoService service;

    @GET
    public Response findAll() {
        List<EnderecoResponseDTO> lista = service.findAll().stream()
                .map(EnderecoMapper::toResponseDTO)
                .toList();
        return Response.ok(lista).build();
    }
}