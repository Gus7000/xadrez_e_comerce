package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.model.RelogioAnalogico;
import br.unitins.tp1.xadrez.e.comerce.service.RelogioService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class RelogioClienteResourceTest {

    @InjectMock
    RelogioService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    void shouldReturnAllRelogios() {
        when(service.findAll()).thenReturn(List.of(buildRelogio(1L), buildRelogio(2L)));

        given()
                .when()
                .get("/cliente/relogio")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", equalTo(1));
    }

    @Test
    void shouldReturnRelogioByMarca() {
        when(service.findByMarca("Marca X")).thenReturn(List.of(buildRelogio(1L)));

        given()
                .when()
            .get("/cliente/relogio/find/marca/Marca X")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].id", equalTo(1));
    }

    @Test
    void shouldReturnRelogioByTipo() {
        when(service.findByTipo(1L)).thenReturn(List.of(buildRelogio(1L)));

        given()
                .when()
                .get("/cliente/relogio/find/tipo/1")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].id", equalTo(1));
    }

    @Test
    void shouldReturnRelogioById() {
        when(service.findById(1L)).thenReturn(buildRelogio(1L));

        given()
                .when()
                .get("/cliente/relogio/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("modelo", is("Modelo 1"));
    }

    private RelogioAnalogico buildRelogio(Long id) {
        RelogioAnalogico relogio = new RelogioAnalogico();
        relogio.setId(id);
        relogio.setModelo("Modelo " + id);
        relogio.setDimensoes("10x10");
        return relogio;
    }
}