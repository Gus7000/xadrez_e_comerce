package br.unitins.tp1.xadrez.e.comerce.resource;

import br.unitins.tp1.xadrez.e.comerce.mapper.ListaDesejosMapper;
import br.unitins.tp1.xadrez.e.comerce.model.ListaDesejos;
import br.unitins.tp1.xadrez.e.comerce.service.ListaDesejosService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/me/lista-desejos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("CLIENTE")
public class MeListaDesejosResource {

    @Inject
    ListaDesejosService listaDesejosService;

    @GET
    public Response getMyLista() {
        ListaDesejos lista = listaDesejosService.findMyLista();
        return Response.ok(ListaDesejosMapper.toResponseDTO(lista)).build();
    }

    @POST
    @Path("/{jogoId}")
    public Response addItem(@PathParam("jogoId") Long jogoId) {
        listaDesejosService.addItem(jogoId);
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{jogoId}")
    public Response removeItem(@PathParam("jogoId") Long jogoId) {
        listaDesejosService.removeItem(jogoId);
        return Response.noContent().build();
    }
}