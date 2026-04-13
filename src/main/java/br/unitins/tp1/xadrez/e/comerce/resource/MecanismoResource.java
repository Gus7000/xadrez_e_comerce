package br.unitins.tp1.xadrez.e.comerce.resource;
import java.util.Arrays;
import java.util.List;


import br.unitins.tp1.xadrez.e.comerce.model.Mecanismo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/mecanismo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class MecanismoResource {
    
    @GET
    public Response findAll() {
        List<Mecanismo> mecanismo = Arrays.asList(Mecanismo.values());
        return Response.ok(mecanismo).build();
    }
    
}
