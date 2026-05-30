package br.unitins.tp1.xadrez.e.comerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoItemRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.CupomDesconto;
import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.model.PedidoStatus;
import br.unitins.tp1.xadrez.e.comerce.model.TipoCupomDesconto;
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
        usuario.setKeycloakId("kc-cliente-1");
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
        usuario.setKeycloakId("kc-cliente-1");
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
        assertEquals(BigDecimal.valueOf(20), pedido.getFrete());
        assertEquals(BigDecimal.valueOf(40.0), pedido.getValorTotal());
    }

    @Test
    void shouldApplyFixedCouponDiscount() {
        Usuario usuario = buildUsuarioCompleto();
        JogoXadrez jogo = buildJogo(100.0);
        CupomDesconto cupom = buildCupom(TipoCupomDesconto.FIXO, BigDecimal.valueOf(25));

        when(usuarioRepository.findById(1L)).thenReturn(usuario);
        when(jogoXadrezRepository.findById(1L)).thenReturn(jogo);
        when(cupomRepository.findById(10L)).thenReturn(cupom);

        PedidoRequestDTO dto = new PedidoRequestDTO(
                1L,
                List.of(new PedidoItemRequestDTO(1L, 1, null)),
                10L,
                PedidoStatus.AGUARDANDO_PAGAMENTO);

        Pedido pedido = pedidoService.create(dto);

        assertEquals(BigDecimal.valueOf(100.0), pedido.getSubtotal());
        assertEquals(BigDecimal.valueOf(25), pedido.getDesconto());
        assertEquals(BigDecimal.valueOf(20), pedido.getFrete());
        assertEquals(BigDecimal.valueOf(95.0), pedido.getValorTotal());
    }

    @Test
    void shouldApplyPercentualCouponDiscount() {
        Usuario usuario = buildUsuarioCompleto();
        JogoXadrez jogo = buildJogo(100.0);
        CupomDesconto cupom = buildCupom(TipoCupomDesconto.PERCENTUAL, BigDecimal.valueOf(10));

        when(usuarioRepository.findById(1L)).thenReturn(usuario);
        when(jogoXadrezRepository.findById(1L)).thenReturn(jogo);
        when(cupomRepository.findById(20L)).thenReturn(cupom);

        PedidoRequestDTO dto = new PedidoRequestDTO(
                1L,
                List.of(new PedidoItemRequestDTO(1L, 2, null)),
                20L,
                PedidoStatus.AGUARDANDO_PAGAMENTO);

        Pedido pedido = pedidoService.create(dto);

        assertEquals(BigDecimal.valueOf(200.0), pedido.getSubtotal());
        assertEquals(BigDecimal.valueOf(20.0), pedido.getDesconto());
        assertEquals(BigDecimal.valueOf(20), pedido.getFrete());
        assertEquals(BigDecimal.valueOf(200.0), pedido.getValorTotal());
    }

    @Test
    void shouldApplyFreeShippingCoupon() {
        Usuario usuario = buildUsuarioCompleto();
        JogoXadrez jogo = buildJogo(100.0);
        CupomDesconto cupom = buildCupom(TipoCupomDesconto.FRETEGRATIS, BigDecimal.ZERO);

        when(usuarioRepository.findById(1L)).thenReturn(usuario);
        when(jogoXadrezRepository.findById(1L)).thenReturn(jogo);
        when(cupomRepository.findById(30L)).thenReturn(cupom);

        PedidoRequestDTO dto = new PedidoRequestDTO(
                1L,
                List.of(new PedidoItemRequestDTO(1L, 1, null)),
                30L,
                PedidoStatus.AGUARDANDO_PAGAMENTO);

        Pedido pedido = pedidoService.create(dto);

        assertEquals(BigDecimal.valueOf(100.0), pedido.getSubtotal());
        assertEquals(BigDecimal.ZERO, pedido.getDesconto());
        assertEquals(BigDecimal.ZERO, pedido.getFrete());
        assertEquals(BigDecimal.valueOf(100.0), pedido.getValorTotal());
    }

    private Usuario buildUsuarioCompleto() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("cliente@mail.com");
        usuario.setKeycloakId("kc-cliente-1");
        usuario.setCadastroCompleto(true);
        return usuario;
    }

    private JogoXadrez buildJogo(double preco) {
        JogoXadrez jogo = new JogoXadrez();
        jogo.setId(1L);
        jogo.setNome("Jogo Teste");
        jogo.setPreco(preco);
        return jogo;
    }

    private CupomDesconto buildCupom(TipoCupomDesconto tipo, BigDecimal valor) {
        CupomDesconto cupom = new CupomDesconto();
        cupom.setId(1L);
        cupom.setCodigo("CUPOM-TESTE");
        cupom.setTipo(tipo);
        cupom.setAtivo(true);
        cupom.setValor(valor);
        cupom.setPorUsuario(false);
        cupom.setUsoMaximo(100);
        cupom.setUsosRealizados(0);
        return cupom;
    }
}
