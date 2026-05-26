package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.DTO.PagamentoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.MetodoPagamento;
import br.unitins.tp1.xadrez.e.comerce.model.Pagamento;
import br.unitins.tp1.xadrez.e.comerce.model.PagamentoStatus;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.service.PagamentoService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;

@QuarkusTest
@TestSecurity(user = "admin", roles = { "ADMIN" })
class PagamentoResourceTest {

    @InjectMock
    PagamentoService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    void shouldReturnAllPagamentos() {
        when(service.findAll()).thenReturn(List.of(buildPagamento(1L, 1L), buildPagamento(2L, 2L)));

        given()
                .when()
                .get("/admin/pagamento")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    void shouldCreatePagamentoSuccessfully() {
        when(service.create(any(PagamentoRequestDTO.class))).thenReturn(buildPagamento(1L, 1L));

        given()
                .contentType(ContentType.JSON)
                .body("{\"pedidoId\":1,\"metodo\":\"PIX\",\"status\":\"PENDENTE\",\"identificadorTransacao\":\"TX123\",\"valor\":100.00}")
                .when()
                .post("/admin/pagamento")
                .then()
                .statusCode(201)
                .body("id", equalTo(1))
                .body("pedidoId", equalTo(1))
                .body("metodo", is("PIX"));
    }

    @Test
    void shouldFailCreateWhenPayloadIsInvalid() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"pedidoId\":null,\"metodo\":null,\"status\":null,\"valor\":0}")
                .when()
                .post("/admin/pagamento")
                .then()
                .statusCode(422)
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", is("Um ou mais campos não passaram na validação."))
                .body("instance", is("/admin/pagamento"))
                .body("errors.size()", greaterThanOrEqualTo(1));
    }

    @Test
    void shouldUpdatePagamentoSuccessfully() {
        doNothing().when(service).update(eq(1L), any(PagamentoRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"pedidoId\":1,\"metodo\":\"CARTAO_CREDITO\",\"status\":\"AUTORIZADO\",\"identificadorTransacao\":\"TX456\",\"valor\":120.00}")
                .when()
                .put("/admin/pagamento/1")
                .then()
                .statusCode(204);

        verify(service).update(eq(1L), any(PagamentoRequestDTO.class));
    }

    @Test
    void shouldFailDeleteWhenPagamentoNotFound() {
        doThrow(new NotFoundException("Pagamento não encontrado")).when(service).delete(999L);

        given()
                .when()
                .delete("/admin/pagamento/999")
                .then()
                .statusCode(404)
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", is("Pagamento não encontrado"))
                .body("instance", is("/admin/pagamento/999"));
    }

    private Pagamento buildPagamento(Long id, Long pedidoId) {
        Pedido pedido = new Pedido();
        pedido.setId(pedidoId);

        Pagamento pagamento = new Pagamento();
        pagamento.setId(id);
        pagamento.setPedido(pedido);
        pagamento.setMetodo(MetodoPagamento.PIX);
        pagamento.setStatus(PagamentoStatus.PENDENTE);
        pagamento.setIdentificadorTransacao("TX123");
        pagamento.setValor(BigDecimal.valueOf(100.0));

        return pagamento;
    }
}
