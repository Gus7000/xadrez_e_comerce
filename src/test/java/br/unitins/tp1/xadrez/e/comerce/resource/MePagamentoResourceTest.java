package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.model.MetodoPagamento;
import br.unitins.tp1.xadrez.e.comerce.model.Pagamento;
import br.unitins.tp1.xadrez.e.comerce.model.PagamentoStatus;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.PagamentoService;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

@QuarkusTest
@TestSecurity(user = "cliente@mail.com", roles = { "CLIENTE" })
class MePagamentoResourceTest {

    @InjectMock
    UsuarioService usuarioService;

    @InjectMock
    PagamentoService pagamentoService;

    @BeforeEach
    void setUp() {
        reset(usuarioService);
        reset(pagamentoService);
    }

    @Test
    void shouldReturnMyPagamentoByPedido() {
        when(usuarioService.findByKeycloakId("cliente@mail.com")).thenReturn(buildUsuario(1L, "cliente@mail.com"));
        when(pagamentoService.findByPedidoId(1L)).thenReturn(buildPagamento(1L, 1L));

        given()
                .when()
                .get("/me/pedidos/1/pagamento")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("pedidoId", equalTo(1))
                .body("metodo", is("PIX"));
    }

    private Usuario buildUsuario(Long id, String keycloakId) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail("cliente" + id + "@mail.com");
        usuario.setKeycloakId(keycloakId);
        return usuario;
    }

    private Pagamento buildPagamento(Long id, Long pedidoId) {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);
        pedido.setUsuario(usuario);

        Pagamento pagamento = new Pagamento();
        pagamento.setId(id);
        pagamento.setPedido(pedido);
        pagamento.setMetodo(MetodoPagamento.PIX);
        pagamento.setStatus(PagamentoStatus.PENDENTE);
        pagamento.setIdentificadorTransacao("TX123");
        pagamento.setValor(BigDecimal.valueOf(100.0));
        pagamento.setDataCadastro(LocalDateTime.of(2026, 5, 8, 10, 0));
        return pagamento;
    }
}