package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.DTO.AuthRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.AuthResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioRegisterDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Perfil;
import br.unitins.tp1.xadrez.e.comerce.service.AuthService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.NotAuthorizedException;

@QuarkusTest
class AuthResourceTest {

    @InjectMock
    AuthService authService;

    @BeforeEach
    void setUp() {
        reset(authService);
    }

    @Test
    void shouldLoginSuccessfully() {
        AuthResponseDTO response = new AuthResponseDTO("admin", "token-jwt", Perfil.ADMIN);
        when(authService.login(any(AuthRequestDTO.class))).thenReturn(response);

        given()
                .contentType(ContentType.JSON)
                .body("{\"login\":\"admin\",\"senha\":\"123456\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("login", equalTo("admin"))
                .body("token", notNullValue())
                .body("perfil", equalTo("ADMIN"));
    }

    @Test
    void shouldFailRegisterWhenLoginAlreadyExists() {
        when(authService.register(any(UsuarioRegisterDTO.class)))
                .thenThrow(new WebApplicationException("Login já cadastrado", 409));

        given()
                .contentType(ContentType.JSON)
                .body("{\"login\":\"cliente\",\"senha\":\"123456\"}")
                .when()
                .post("/auth/register")
                .then()
                .statusCode(409);
    }

    @Test
    void shouldFailLoginWithMissingFields() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"login\":\"admin\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(422);
    }

    @Test
    void shouldFailLoginWithInvalidCredentials() {
        when(authService.login(any(AuthRequestDTO.class)))
                .thenThrow(new NotAuthorizedException("Login ou senha inválidos"));

        given()
                .contentType(ContentType.JSON)
                .body("{\"login\":\"admin\",\"senha\":\"senha-errada\"}")
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);
    }
}
