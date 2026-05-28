package br.unitins.tp1.xadrez.e.comerce.resource;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.MePedidoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoRequestDTO;

import br.unitins.tp1.xadrez.e.comerce.mapper.PedidoMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.PedidoService;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.NotFoundException;

@Path("/me/pedidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"CLIENTE","ADMIN"})
public class MePedidoResource {

    @Inject
    UsuarioService usuarioService;

    @Inject
    PedidoService pedidoService;

    @GET
    public Response listMyPedidos() {
        Usuario usuario = usuarioService.obterOuCriarUsuarioLocal();
        List<Pedido> pedidos = pedidoService.findByUsuarioId(usuario.getId());
        return Response.ok(pedidos.stream().map(PedidoMapper::toResponseDTO).toList()).build();
    }

    @GET
    @Path("/{id}")
    public Response getMyPedido(@PathParam("id") Long id) {
        Usuario usuario = usuarioService.obterOuCriarUsuarioLocal();
        Pedido pedido = pedidoService.findById(id);
        if (pedido.getUsuario() == null || !pedido.getUsuario().getId().equals(usuario.getId())) {
            throw new NotFoundException("Pedido não encontrado");
        }
        return Response.ok(PedidoMapper.toResponseDTO(pedido)).build();
    }

    @POST
    @Transactional
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
}
