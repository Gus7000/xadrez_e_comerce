package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;
import br.unitins.tp1.xadrez.e.comerce.service.TabuleiroService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class TabuleiroClienteResourceTest {

    @InjectMock
    TabuleiroService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    void shouldReturnAllTabuleiros() {
        when(service.findAll()).thenReturn(List.of(buildTabuleiro(1L), buildTabuleiro(2L)));

        given()
                .when()
            .get("/cliente/tabuleiro")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", equalTo(1));
    }

    @Test
    void shouldReturnTabuleiroById() {
        when(service.findById(1L)).thenReturn(buildTabuleiro(1L));

        given()
                .when()
            .get("/cliente/tabuleiro/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("tamanho", is("40x40"));
    }

    @Test
    void shouldReturnTabuleirosByTamanho() {
        when(service.findByTamanho("40x40")).thenReturn(List.of(buildTabuleiro(1L)));

        given()
                .when()
            .get("/cliente/tabuleiro/find/tamanho/40x40")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].tamanho", is("40x40"));
    }

    @Test
    void shouldReturnTabuleirosByMaterial() {
        when(service.findByMaterial(1L)).thenReturn(List.of(buildTabuleiro(1L)));

        given()
                .when()
            .get("/cliente/tabuleiro/find/material/1")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].id", equalTo(1));
    }

    private Tabuleiro buildTabuleiro(Long id) {
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setId(id);
        tabuleiro.setTamanho("40x40");
        return tabuleiro;
    }
}