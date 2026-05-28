package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.model.CupomDesconto;
import br.unitins.tp1.xadrez.e.comerce.model.CupomDescontoFixo;
import br.unitins.tp1.xadrez.e.comerce.model.CupomDescontoPercentual;
import br.unitins.tp1.xadrez.e.comerce.service.CupomDescontoService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestSecurity(user = "admin", roles = { "ADMIN" })
class CupomDescontoResourceTest {

    @InjectMock
    CupomDescontoService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    void shouldReturnAllCupons() {
        when(service.findAll()).thenReturn(List.of(buildFixo(1L), buildPercentual(2L)));

        given()
                .when()
                .get("/admin/cupom")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].codigo", is("CUPOM10"))
                .body("[1].tipo", is("PERCENTUAL"));
    }

    @Test
    void shouldReturnCupomById() {
        when(service.findById(1L)).thenReturn(buildFixo(1L));

        given()
                .when()
                .get("/admin/cupom/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("codigo", is("CUPOM10"))
                .body("tipo", is("FIXO"));
    }

    @Test
    void shouldCreateCupomSuccessfully() {
        when(service.create(any(CupomDesconto.class))).thenReturn(buildPercentual(1L));

        given()
                .contentType(ContentType.JSON)
                .body("{\"codigo\":\"CUPOM15\",\"tipo\":\"PERCENTUAL\",\"dataValidade\":\"2026-12-31\",\"ativo\":true,\"usoMaximo\":10,\"porUsuario\":true,\"percentualDesconto\":15.0,\"valorDesconto\":null}")
                .when()
                .post("/admin/cupom")
                .then()
                .statusCode(201)
                .body("id", equalTo(1))
                .body("codigo", is("CUPOM15"))
                .body("tipo", is("PERCENTUAL"))
                .body("percentualDesconto", equalTo(15.0F));
    }

    @Test
    void shouldUpdateCupomSuccessfully() {
        doNothing().when(service).update(eq(1L), any(CupomDesconto.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"codigo\":\"CUPOM20\",\"tipo\":\"FIXO\",\"dataValidade\":\"2026-12-31\",\"ativo\":true,\"usoMaximo\":5,\"porUsuario\":false,\"percentualDesconto\":null,\"valorDesconto\":20.0}")
                .when()
                .put("/admin/cupom/1")
                .then()
                .statusCode(204);

        verify(service).update(eq(1L), any(CupomDesconto.class));
    }

    @Test
    void shouldDeleteCupomSuccessfully() {
        doNothing().when(service).delete(1L);

        given()
                .when()
                .delete("/admin/cupom/1")
                .then()
                .statusCode(204);

        verify(service).delete(1L);
    }

    private CupomDescontoFixo buildFixo(Long id) {
        CupomDescontoFixo cupom = new CupomDescontoFixo();
        cupom.setId(id);
        cupom.setCodigo("CUPOM10");
        cupom.setDataValidade(LocalDate.of(2026, 12, 31));
        cupom.setAtivo(true);
        cupom.setUsoMaximo(10);
        cupom.setUsosRealizados(2);
        cupom.setPorUsuario(false);
        cupom.setValorDesconto(BigDecimal.valueOf(10.0));
        cupom.setDataCadastro(LocalDateTime.of(2026, 5, 8, 10, 0));
        return cupom;
    }

    private CupomDescontoPercentual buildPercentual(Long id) {
        CupomDescontoPercentual cupom = new CupomDescontoPercentual();
        cupom.setId(id);
        cupom.setCodigo("CUPOM15");
        cupom.setDataValidade(LocalDate.of(2026, 12, 31));
        cupom.setAtivo(true);
        cupom.setUsoMaximo(10);
        cupom.setUsosRealizados(1);
        cupom.setPorUsuario(true);
        cupom.setPercentualDesconto(15.0);
        cupom.setDataCadastro(LocalDateTime.of(2026, 5, 8, 10, 0));
        return cupom;
    }
}