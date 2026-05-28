package br.unitins.tp1.xadrez.e.comerce.resource;

import br.unitins.tp1.xadrez.e.comerce.model.Pagamento;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.PagamentoService;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.NotFoundException;
import io.quarkus.security.identity.SecurityIdentity;
import br.unitins.tp1.xadrez.e.comerce.mapper.PagamentoMapper;

@Path("/me/pedidos/{idPedido}/pagamento")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("CLIENTE")
public class MePagamentoResource {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    UsuarioService usuarioService;

    @Inject
    PagamentoService pagamentoService;

    private Usuario currentUsuario() {
        return usuarioService.findByKeycloakId(securityIdentity.getPrincipal().getName());
    }

    @GET
    public Response getMyPagamento(@PathParam("idPedido") Long idPedido) {
        Usuario usuario = currentUsuario();
        Pagamento pagamento = pagamentoService.findByPedidoId(idPedido);
        if (pagamento.getPedido() == null || pagamento.getPedido().getUsuario() == null
                || !pagamento.getPedido().getUsuario().getId().equals(usuario.getId())) {
            throw new NotFoundException("Pagamento não encontrado");
        }
        return Response.ok(PagamentoMapper.toResponseDTO(pagamento)).build();
    }
}
