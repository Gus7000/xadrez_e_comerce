package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestSecurity(user = "cliente@mail.com", roles = { "CLIENTE" })
class MePedidoResourceTest {

    @InjectMock
    UsuarioService usuarioService;

    @InjectMock
    PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        reset(usuarioService);
        reset(pedidoService);
    }

    @Test
    void shouldReturnMyPedidos() {
        when(usuarioService.findByKeycloakId("cliente@mail.com")).thenReturn(buildUsuario(1L, true));
        when(pedidoService.findByUsuarioId(1L)).thenReturn(List.of(buildPedido(1L, 1L)));

        given()
                .when()
                .get("/me/pedidos")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].id", equalTo(1))
                .body("[0].usuarioId", equalTo(1));
    }

    @Test
    void shouldReturnMyPedidoById() {
        when(usuarioService.findByKeycloakId("cliente@mail.com")).thenReturn(buildUsuario(1L, true));
        when(pedidoService.findById(1L)).thenReturn(buildPedido(1L, 1L));

        given()
                .when()
                .get("/me/pedidos/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("usuarioId", equalTo(1));
    }

    @Test
    void shouldCreateMyPedidoSuccessfully() {
        when(usuarioService.findByKeycloakId("cliente@mail.com")).thenReturn(buildUsuario(1L, true));
        when(pedidoService.create(any(PedidoRequestDTO.class))).thenReturn(buildPedido(1L, 1L));

        given()
                .contentType(ContentType.JSON)
                .body("{\"items\":[{\"jogoId\":1,\"quantidade\":2,\"precoUnitario\":100.00}],\"cupomId\":null,\"status\":\"AGUARDANDO_PAGAMENTO\"}")
                .when()
                .post("/me/pedidos")
                .then()
                .statusCode(201)
                .body("id", equalTo(1))
                .body("usuarioId", equalTo(1))
                .body("items.size()", is(1));
    }

    @Test
    void shouldRejectCreateWhenCadastroIsIncomplete() {
        when(usuarioService.findByKeycloakId("cliente@mail.com")).thenReturn(buildUsuario(1L, false));

        given()
                .contentType(ContentType.JSON)
                .body("{\"items\":[{\"jogoId\":1,\"quantidade\":2,\"precoUnitario\":100.00}],\"cupomId\":null,\"status\":\"AGUARDANDO_PAGAMENTO\"}")
                .when()
                .post("/me/pedidos")
                .then()
                .statusCode(400)
                .body(containsString("É necessário completar o cadastro antes de fazer um pedido."));
    }

    private Usuario buildUsuario(Long id, boolean cadastroCompleto) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail("cliente" + id + "@mail.com");
        usuario.setCadastroCompleto(cadastroCompleto);
        return usuario;
    }

    private Pedido buildPedido(Long id, Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setEmail("cliente" + usuarioId + "@mail.com");

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
        pedido.setDataCadastro(LocalDateTime.of(2026, 5, 8, 10, 0));
        return pedido;
    }
}