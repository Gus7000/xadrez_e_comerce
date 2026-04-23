package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
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

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.model.Relogio;
import br.unitins.tp1.xadrez.e.comerce.model.RelogioDigital;
import br.unitins.tp1.xadrez.e.comerce.service.RelogioService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
class RelogioResourceTest {

    @InjectMock
    RelogioService relogioService;

    @BeforeEach
    void setUp() {
        reset(relogioService);
    }

    @Test
    void shouldReturnAllRelogios() {
        when(relogioService.findAll()).thenReturn(List.of(buildRelogio(1L), buildRelogio(2L)));

        given()
                .when()
                .get("/relogio")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

  

    @Test
    void shouldCreateValidRelogio() {
        when(relogioService.create(any(RelogioRequestDTO.class))).thenReturn(buildRelogio(1L));

        given()
                .contentType(ContentType.JSON)
                .body("{\"modelo\":\"Clock X\",\"dimensoes\":\"20x20\",\"fabricanteId\":1}")
                .when()
                .post("/relogio")
                .then()
                .statusCode(201)
                .body("id", equalTo(1));
    }

    @Test
    void shouldFailCreateWithInvalidData() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"modelo\":\"\",\"dimensoes\":\"20x20\",\"fabricanteId\":1}")
                .when()
                .post("/relogio")
                .then()
                .statusCode(422)
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", equalTo("Um ou mais campos não passaram na validação."))
                .body("instance", is("/relogio"))
                .body("timestamp", notNullValue())
                .body("errors.size()", greaterThanOrEqualTo(1))
                .body("errors[0].field", notNullValue())
                .body("errors[0].message", notNullValue());
    }

    @Test
    void shouldFailCreateWhenFabricanteNotFound() {
        doThrow(new jakarta.ws.rs.NotFoundException("Fabricante não encontrado"))
            .when(relogioService).create(any(RelogioRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"modelo\":\"Clock X\",\"dimensoes\":\"20x20\",\"fabricanteId\":99}")
                .when()
                .post("/relogio")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("Fabricante não encontrado"))
                .body("instance", is("/relogio"))
                .body("timestamp", notNullValue());
    }

    @Test
    void shouldUpdateRelogioSuccessfully() {
        doNothing().when(relogioService).update(eq(1L), any(RelogioRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"modelo\":\"Clock X\",\"dimensoes\":\"20x20\",\"fabricanteId\":1}")
                .when()
                .put("/relogio/1")
                .then()
                .statusCode(204);

        verify(relogioService).update(eq(1L), any(RelogioRequestDTO.class));
    }

    @Test
    void shouldFailUpdateWhenFabricanteNotFound() {
        doThrow(new jakarta.ws.rs.NotFoundException("Fabricante não encontrado"))
            .when(relogioService).update(eq(1L), any(RelogioRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"modelo\":\"Clock X\",\"dimensoes\":\"20x20\",\"fabricanteId\":99}")
                .when()
                .put("/relogio/1")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("Fabricante não encontrado"))
                .body("instance", is("/relogio/1"))
                .body("timestamp", notNullValue());
    }

    @Test
    void shouldDeleteRelogioSuccessfully() {
        doNothing().when(relogioService).delete(1L);

        given()
                .when()
                .delete("/relogio/1")
                .then()
                .statusCode(204);

        verify(relogioService).delete(1L);
    }

    @Test
    void shouldFindRelogioByTipo() {
        when(relogioService.findByTipo(1L)).thenReturn(List.of(buildRelogio(1L), buildRelogio(2L)));

        given()
                .when()
                .get("/relogio/find/tipo/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(2));
    }

    @Test
    void shouldReturnEmptyListWhenFindByTipoNotFound() {
        when(relogioService.findByTipo(99L)).thenReturn(List.of());

        given()
                .when()
                .get("/relogio/find/tipo/99")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", is(0));
    }

    @Test
    void shouldFailCreateWithInvalidDataReturningProblemJson() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"modelo\":\"\",\"dimensoes\":\"20x20\",\"fabricanteId\":1}")
                .when()
                .post("/relogio")
                .then()
                .statusCode(422)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", equalTo("Um ou mais campos não passaram na validação."))
                .body("instance", is("/relogio"))
                .body("timestamp", notNullValue())
                .body("errors.size()", greaterThanOrEqualTo(1))
                .body("errors[0].field", notNullValue())
                .body("errors[0].message", notNullValue());
    }

    private Relogio buildRelogio(Long id) {
        RelogioDigital relogio = new RelogioDigital();
        relogio.setId(id);
        relogio.setModelo("Clock X");
        relogio.setDimensoes("20x20");
        relogio.setFabricante(buildFabricante(1L));
        relogio.setDisplayDuplo(true);
        relogio.setTemBuzzer(true);
        return relogio;
    }

    private Fabricante buildFabricante(Long id) {
        Fabricante fabricante = new Fabricante();
        fabricante.setId(id);
        fabricante.setNome("Fab");
        fabricante.setCnpj("04252011000110");
        fabricante.setTelefone("(63) 99999-9999");
        return fabricante;
    }
}
