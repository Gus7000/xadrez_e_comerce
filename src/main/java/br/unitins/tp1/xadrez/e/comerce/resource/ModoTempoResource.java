package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.Arrays;
import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.ModoTempo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/modo-tempo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ModoTempoResource {

    @GET
    public Response findAll() {
        List<ModoTempo> modos = Arrays.asList(ModoTempo.values());
        return Response.ok(modos).build();
    }
}
