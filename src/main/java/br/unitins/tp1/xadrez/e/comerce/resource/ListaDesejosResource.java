package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.ListaDesejosResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.ListaDesejosMapper;
import br.unitins.tp1.xadrez.e.comerce.service.ListaDesejosService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin/utilizadores/{utilizadorId}/lista-desejos")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class ListaDesejosResource {

    @Inject
    ListaDesejosService listaDesejosService;

    @GET
    public Response findByUsuarioId(@PathParam("utilizadorId") Long utilizadorId) {
        List<ListaDesejosResponseDTO> response = listaDesejosService.findByUsuarioId(utilizadorId)
                .stream()
                .map(ListaDesejosMapper::toResponseDTO)
                .toList();

        return Response.ok(response).build();
    }
}