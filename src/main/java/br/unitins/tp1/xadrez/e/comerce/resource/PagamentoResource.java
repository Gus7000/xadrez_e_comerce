package br.unitins.tp1.xadrez.e.comerce.resource;

import br.unitins.tp1.xadrez.e.comerce.mapper.PagamentoMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Pagamento;
import br.unitins.tp1.xadrez.e.comerce.service.PagamentoService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/admin/pagamentos")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("ADMIN")
public class PagamentoResource {

    @Inject
    PagamentoService service;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Long id) {
        Pagamento pagamento = service.findById(id);
        return Response.ok(PagamentoMapper.toResponseDTO(pagamento)).build();
    }
}
