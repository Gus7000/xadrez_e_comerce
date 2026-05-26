package br.unitins.tp1.xadrez.e.comerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoItemRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Perfil;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.model.PedidoStatus;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.repository.CupomDescontoRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.JogoXadrezRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.PedidoItemRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.PedidoRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.UsuarioRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;

@QuarkusTest
class PedidoServiceImplTest {

    @Inject
    PedidoService pedidoService;

    @InjectMock
    PedidoRepository pedidoRepository;

    @InjectMock
    PedidoItemRepository pedidoItemRepository;

    @InjectMock
    UsuarioRepository usuarioRepository;

    @InjectMock
    JogoXadrezRepository jogoXadrezRepository;

    @InjectMock
    CupomDescontoRepository cupomRepository;

    @Test
    void shouldRejectPedidoWhenCadastroIsIncomplete() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("cliente@mail.com");
        usuario.setPerfil(Perfil.CLIENTE);
        usuario.setCadastroCompleto(false);

        when(usuarioRepository.findById(1L)).thenReturn(usuario);

        PedidoRequestDTO dto = new PedidoRequestDTO(
                1L,
                List.of(new PedidoItemRequestDTO(1L, 1, BigDecimal.TEN)),
                null,
                PedidoStatus.AGUARDANDO_PAGAMENTO);

        WebApplicationException exception = assertThrows(WebApplicationException.class, () -> pedidoService.create(dto));

        assertEquals(412, exception.getResponse().getStatus());
    }

    @Test
    void shouldCreatePedidoWhenCadastroIsComplete() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("cliente@mail.com");
        usuario.setPerfil(Perfil.CLIENTE);
        usuario.setCadastroCompleto(true);

        when(usuarioRepository.findById(1L)).thenReturn(usuario);
        when(jogoXadrezRepository.findById(1L)).thenAnswer(invocation -> {
            br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez jogo = new br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez();
            jogo.setId(1L);
            jogo.setNome("Jogo Teste");
            jogo.setPreco(10.0);
            return jogo;
        });

        PedidoRequestDTO dto = new PedidoRequestDTO(
                1L,
                List.of(new PedidoItemRequestDTO(1L, 2, null)),
                null,
                PedidoStatus.AGUARDANDO_PAGAMENTO);

        Pedido pedido = pedidoService.create(dto);

        assertEquals(1L, pedido.getUsuario().getId());
        assertEquals(PedidoStatus.AGUARDANDO_PAGAMENTO, pedido.getStatus());
        assertEquals(BigDecimal.valueOf(20.0), pedido.getSubtotal());
    }
}
