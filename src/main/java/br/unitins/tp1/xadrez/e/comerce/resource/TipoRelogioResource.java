package br.unitins.tp1.xadrez.e.comerce.resource;

import br.unitins.tp1.xadrez.e.comerce.model.TipoRelogio;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/tipoRelogio")
public class TipoRelogioResource {
    
    @GET
    public Response findAll(){
        return Response.ok(TipoRelogio.values()).build();
    }
    @GET
    @Path("/{id}")
    public Response findById(Long id){
        return Response.ok(TipoRelogio.valueOf(id)).build();
    }
}
