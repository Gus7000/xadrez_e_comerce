package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.MePedidoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.PedidoMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.model.Pagamento;
import br.unitins.tp1.xadrez.e.comerce.service.PedidoService;
import br.unitins.tp1.xadrez.e.comerce.service.PagamentoService;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.NotFoundException;
import io.quarkus.security.identity.SecurityIdentity;

@Path("/me/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("CLIENTE")
public class MePedidoResource {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    UsuarioService usuarioService;

    @Inject
    PedidoService pedidoService;

    @Inject
    PagamentoService pagamentoService;

    private Usuario currentUsuario() {
        return usuarioService.obterOuCriarUsuarioLocal();
    }

    @GET
    public Response listMyPedidos(@QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size) {
        Usuario usuario = currentUsuario();
        List<Pedido> pedidos = pedidoService.findByUsuarioId(usuario.getId(), page, size);
        return Response.ok(pedidos.stream().map(PedidoMapper::toResponseDTO).toList()).build();
    }

    @GET
    @Path("/{id}")
    public Response getMyPedido(@PathParam("id") Long id) {
        Usuario usuario = currentUsuario();
        Pedido pedido = pedidoService.findById(id);
        if (pedido.getUsuario() == null || !pedido.getUsuario().getId().equals(usuario.getId())) {
            throw new NotFoundException("Pedido não encontrado");
        }
        return Response.ok(PedidoMapper.toResponseDTO(pedido)).build();
    }

    @POST
    public Response createMyPedido(@Valid MePedidoRequestDTO dto) {
        Usuario usuario = usuarioService.obterOuCriarUsuarioLocal();

        if (!usuario.isCadastroCompleto()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("É necessário completar o cadastro antes de fazer um pedido.")
                    .build();
        }

        PedidoRequestDTO createDto = new PedidoRequestDTO(
                usuario.getId(),
                dto.items(),
                dto.cupomId(),
                dto.status());

        Pedido created = pedidoService.create(createDto);
        return Response.status(Response.Status.CREATED).entity(PedidoMapper.toResponseDTO(created)).build();
    }

    @GET
    @Path("/{idPedido}/pagamento")
    public Response getPagamentoDoPedido(@PathParam("idPedido") Long idPedido) {
        Usuario usuario = currentUsuario();
        Pagamento pagamento = pagamentoService.findByPedidoId(idPedido);

        if (pagamento == null || pagamento.getPedido() == null || pagamento.getPedido().getUsuario() == null
                || !pagamento.getPedido().getUsuario().getId().equals(usuario.getId())) {
            throw new NotFoundException("Pagamento não encontrado");
        }

        return Response.ok(br.unitins.tp1.xadrez.e.comerce.mapper.PagamentoMapper.toResponseDTO(pagamento)).build();
    }
}
