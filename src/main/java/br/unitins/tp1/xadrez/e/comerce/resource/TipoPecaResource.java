package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.Arrays;
import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.TipoPeca;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/tipo-peca")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TipoPecaResource {

    @GET
    public Response findAll() {
        List<TipoPeca> tipos = Arrays.asList(TipoPeca.values());
        return Response.ok(tipos).build();
    }
}
