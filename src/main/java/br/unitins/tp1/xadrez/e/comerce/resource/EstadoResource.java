package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.Estado;
import br.unitins.tp1.xadrez.e.comerce.repository.EstadoRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/estado")
public class EstadoResource {

    @Inject
    EstadoRepository repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Estado> hello() {
        return repository.listAll();
    }
}
