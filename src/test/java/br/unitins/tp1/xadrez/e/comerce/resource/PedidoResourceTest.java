package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.model.PedidoItem;
import br.unitins.tp1.xadrez.e.comerce.model.PedidoStatus;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.PedidoService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestSecurity(user = "admin", roles = { "ADMIN" })
class PedidoResourceTest {

    @InjectMock
    PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        reset(pedidoService);
    }

    @Test
    void shouldReturnAllPedidos() {
        when(pedidoService.findAll(0, 20)).thenReturn(List.of(buildPedido(1L, 1L), buildPedido(2L, 2L)));

        given()
                .when()
                .get("/admin/pedidos")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[1].id", equalTo(2));
    }

    @Test
    void shouldReturnPedidoById() {
        when(pedidoService.findById(1L)).thenReturn(buildPedido(1L, 1L));

        given()
                .when()
                .get("/admin/pedidos/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("status", is("AGUARDANDO_PAGAMENTO"));
    }

    @Test
    void shouldUpdateStatusSuccessfully() {
        doNothing().when(pedidoService).updateStatus(1L, PedidoStatus.PAGO);

        given()
                .contentType(ContentType.JSON)
                .body("{\"status\":\"PAGO\"}")
                .when()
                .patch("/admin/pedidos/1/status")
                .then()
                .statusCode(204);

        verify(pedidoService).updateStatus(1L, PedidoStatus.PAGO);
    }

    private Pedido buildPedido(Long id, Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setEmail("u" + usuarioId + "@mail.com");

        JogoXadrez jogo = new JogoXadrez();
        jogo.setId(1L);
        jogo.setNome("Jogo Clássico");

        PedidoItem item = new PedidoItem();
        item.setId(1L);
        item.setJogo(jogo);
        item.setQuantidade(2);
        item.setPrecoUnitario(BigDecimal.valueOf(100.0));

        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setUsuario(usuario);
        pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);
        pedido.setSubtotal(BigDecimal.valueOf(200.0));
        pedido.setDesconto(BigDecimal.ZERO);
        pedido.setFrete(BigDecimal.ZERO);
        pedido.setTaxas(BigDecimal.ZERO);
        pedido.setValorTotal(BigDecimal.valueOf(200.0));
        pedido.setItems(List.of(item));
        return pedido;
    }
}
