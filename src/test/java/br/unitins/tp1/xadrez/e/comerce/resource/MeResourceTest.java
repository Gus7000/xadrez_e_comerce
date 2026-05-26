package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

@QuarkusTest
@TestSecurity(user = "cliente@mail.com", roles = {"CLIENTE"})
class MeResourceTest {

    @Test
    void shouldReturnLoggedUserProfile() {
        given()
                .when()
                .get("/me")
                .then()
                .statusCode(200)
                .body("email", is("cliente@mail.com"))
                .body("perfil", is("CLIENTE"));
    }
}