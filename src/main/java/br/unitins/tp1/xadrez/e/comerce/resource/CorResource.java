package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.Arrays;
import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.CorPeca;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cor")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CorResource {

    @GET
    public Response findAll() {
        List<CorPeca> cores = Arrays.asList(CorPeca.values());
        return Response.ok(cores).build();
    }
}
