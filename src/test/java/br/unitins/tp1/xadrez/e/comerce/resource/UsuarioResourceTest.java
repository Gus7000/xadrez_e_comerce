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

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;

@QuarkusTest
@TestSecurity(user = "admin@mail.com", roles = {"ADMIN"})
class UsuarioResourceTest {

    @InjectMock
    UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        reset(usuarioService);
    }

    @Test
    void shouldReturnAllUsuarios() {
        when(usuarioService.findAll()).thenReturn(List.of(buildUsuario(1L, "admin@mail.com"), buildUsuario(2L, "cliente@mail.com")));

        given()
                .when()
                .get("/admin/usuarios")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].email", is("admin@mail.com"))
                .body("[1].email", is("cliente@mail.com"));
    }

    @Test
    void shouldReturnUsuarioById() {
        when(usuarioService.findById(1L)).thenReturn(buildUsuario(1L, "admin@mail.com"));

        given()
                .when()
                .get("/admin/usuarios/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("email", is("admin@mail.com"))
                .body("keycloakId", is("admin@mail.com"));
    }

    @Test
    void shouldCreateUsuarioSuccessfully() {
        when(usuarioService.create(any(UsuarioRequestDTO.class))).thenReturn(buildUsuario(1L, "admin@mail.com"));

        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"admin@mail.com\",\"keycloakId\":\"kc-admin-1\"}")
                .when()
                .post("/admin/usuarios")
                .then()
                .statusCode(201)
                .body("id", equalTo(1))
                .body("email", is("admin@mail.com"))
                .body("keycloakId", is("admin@mail.com"))
                .body("dataCadastro", notNullValue());
    }

    @Test
    void shouldFailCreateUsuarioWithMissingFields() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"admin\"}")
                .when()
                .post("/admin/usuarios")
                .then()
                .statusCode(422)
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", is("Um ou mais campos não passaram na validação."))
                .body("instance", is("/admin/usuarios"))
                .body("errors.size()", greaterThanOrEqualTo(1));
    }

    @Test
    void shouldUpdateUsuarioSuccessfully() {
        doNothing().when(usuarioService).update(eq(1L), any(UsuarioRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"admin2@mail.com\",\"keycloakId\":\"kc-admin-2\"}")
                .when()
                .put("/admin/usuarios/1")
                .then()
                .statusCode(204);

        verify(usuarioService).update(eq(1L), any(UsuarioRequestDTO.class));
    }

    @Test
    void shouldFailUpdateWhenUsuarioNotFound() {
        doThrow(new NotFoundException("Usuário não encontrado"))
                .when(usuarioService).update(eq(999L), any(UsuarioRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"email\":\"admin2@mail.com\",\"keycloakId\":\"kc-admin-2\"}")
                .when()
                .put("/admin/usuarios/999")
                .then()
                .statusCode(404)
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", is("Usuário não encontrado"))
                .body("instance", is("/admin/usuarios/999"));
    }

    @Test
    void shouldDeleteUsuarioSuccessfully() {
        doNothing().when(usuarioService).delete(1L);

        given()
                .when()
                .delete("/admin/usuarios/1")
                .then()
                .statusCode(204);

        verify(usuarioService).delete(1L);
    }

    @Test
    void shouldFailDeleteWhenUsuarioNotFound() {
        doThrow(new NotFoundException("Usuário não encontrado"))
                .when(usuarioService).delete(999L);

        given()
                .when()
                .delete("/admin/usuarios/999")
                .then()
                .statusCode(404)
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", is("Usuário não encontrado"))
                .body("instance", is("/admin/usuarios/999"));
    }

    private Usuario buildUsuario(Long id, String email) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setKeycloakId(email);
        usuario.setDataCadastro(LocalDateTime.of(2026, 5, 8, 10, 0));
        return usuario;
    }
}
