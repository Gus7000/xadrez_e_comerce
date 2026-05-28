package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.mapper.PagamentoMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Pagamento;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.PagamentoService;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.NotFoundException;

@Path("/me/pagamentos")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed({"CLIENTE","ADMIN"})
public class MePagamentoResource {

    @Inject
    JsonWebToken jwt;

    @Inject
    UsuarioService usuarioService;

    @Inject
    PagamentoService pagamentoService;

    @GET
    public Response listMyPagamentos() {
        String keycloakId = jwt.getSubject();
        Usuario usuario = usuarioService.findByKeycloakId(keycloakId);
        List<Pagamento> pagamentos = pagamentoService.findByUsuarioId(usuario.getId());
        return Response.ok(pagamentos.stream().map(PagamentoMapper::toResponseDTO).toList()).build();
    }

    @GET
    @Path("/{id}")
    public Response getMyPagamento(@PathParam("id") Long id) {
        String keycloakId = jwt.getSubject();
        Usuario usuario = usuarioService.findByKeycloakId(keycloakId);
        Pagamento pagamento = pagamentoService.findById(id);
        if (pagamento.getPedido() == null || pagamento.getPedido().getUsuario() == null
                || !pagamento.getPedido().getUsuario().getId().equals(usuario.getId())) {
            throw new NotFoundException("Pagamento não encontrado");
        }
        return Response.ok(PagamentoMapper.toResponseDTO(pagamento)).build();
    }
}
