package br.unitins.tp1.xadrez.e.comerce.resource;

import br.unitins.tp1.xadrez.e.comerce.model.Regiao;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/regioes")
public class RegiaoResource {
    
    @GET
    public Regiao[] buscarTodos(){
        return Regiao.values();
    }
    @GET
    @Path("/{id}")
    public Regiao buscarPeloId(Long id){
        return Regiao.valueOf(id);
    }
}
