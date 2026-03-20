package br.unitins.tp1.xadrez.e.comerce.resource;

import br.unitins.tp1.xadrez.e.comerce.model.TipoRelogio;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/tipoRelogio")
public class TipoRelogioResource {
    
    @GET
    public TipoRelogio[] buscarTodos(){
        return TipoRelogio.values();
    }
    @GET
    @Path("/{id}")
    public TipoRelogio buscarPeloId(Long id){
        return TipoRelogio.valueOf(id);
    }
}
