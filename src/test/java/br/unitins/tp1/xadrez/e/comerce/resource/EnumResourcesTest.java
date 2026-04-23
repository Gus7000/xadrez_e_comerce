package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class EnumResourcesTest {

    @Test
    void shouldReturnAllCorPeca() {
        given()
                .when()
                .get("/cor")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].nome", is("Preto"))
                .body("[1].nome", is("Branco"));
    }

    @Test
    void shouldReturnAllMecanismo() {
        given()
                .when()
                .get("/mecanismo")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].nome", is("Corda"))
                .body("[1].nome", is("Quartz"));
    }

    @Test
    void shouldReturnAllTipoPeca() {
        given()
                .when()
                .get("/tipo-peca")
                .then()
                .statusCode(200)
                .body("size()", is(6))
                .body("[0].nome", is("Rei"))
                .body("[5].nome", is("Peao"));
    }

    @Test
    void shouldReturnAllModoTempo() {
        given()
                .when()
                .get("/modo-tempo")
                .then()
                .statusCode(200)
                .body("size()", is(4))
                .body("[0].nome", is("Fischer"))
                .body("[3].nome", is("Simples"));
    }
}
