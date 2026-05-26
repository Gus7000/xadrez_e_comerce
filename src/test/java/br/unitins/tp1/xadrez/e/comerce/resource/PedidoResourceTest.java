package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoRequestDTO;
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
import jakarta.ws.rs.NotFoundException;

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
        when(pedidoService.findAll()).thenReturn(List.of(buildPedido(1L, 1L), buildPedido(2L, 2L)));

        given()
                .when()
                .get("/admin/pedido")
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
                .get("/admin/pedido/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("status", is("AGUARDANDO_PAGAMENTO"));
    }

    @Test
    void shouldCreatePedidoSuccessfully() {
        when(pedidoService.create(any(PedidoRequestDTO.class))).thenReturn(buildPedido(1L, 1L));

        given()
                .contentType(ContentType.JSON)
                .body("{\"usuarioId\":1,\"items\":[{\"jogoId\":1,\"quantidade\":2,\"precoUnitario\":100.00}],\"status\":\"AGUARDANDO_PAGAMENTO\"}")
                .when()
                .post("/admin/pedido")
                .then()
                .statusCode(201)
                .body("id", equalTo(1))
                .body("subtotal", equalTo(200.0F))
                .body("items.size()", is(1));
    }

    @Test
    void shouldFailCreateWhenPayloadIsInvalid() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"usuarioId\":null,\"items\":[]}")
                .when()
                .post("/admin/pedido")
                .then()
                .statusCode(422)
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", is("Um ou mais campos não passaram na validação."))
                .body("instance", is("/admin/pedido"))
                .body("errors.size()", greaterThanOrEqualTo(1));
    }

    @Test
    void shouldUpdateStatusSuccessfully() {
        doNothing().when(pedidoService).updateStatus(1L, PedidoStatus.PAGO);

        given()
                .contentType(ContentType.JSON)
                .body("{\"status\":\"PAGO\"}")
                .when()
                .put("/admin/pedido/1/status")
                .then()
                .statusCode(204);

        verify(pedidoService).updateStatus(1L, PedidoStatus.PAGO);
    }

    @Test
    void shouldFailDeleteWhenPedidoNotFound() {
        doThrow(new NotFoundException("Pedido não encontrado")).when(pedidoService).delete(999L);

        given()
                .when()
                .delete("/admin/pedido/999")
                .then()
                .statusCode(404)
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", is("Pedido não encontrado"))
                .body("instance", is("/admin/pedido/999"));
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
