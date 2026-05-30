package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.KeycloakAdminService;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.ws.rs.NotFoundException;
import org.keycloak.representations.idm.UserRepresentation;

@QuarkusTest
@TestSecurity(user = "admin@mail.com", roles = {"ADMIN"})
class UsuarioResourceTest {

    @InjectMock
    UsuarioService usuarioService;

    @InjectMock
    KeycloakAdminService keycloakAdminService;

    @BeforeEach
    void setUp() {
        reset(usuarioService, keycloakAdminService);
    }

    @Test
    void shouldReturnAllUsuarios() {
        when(keycloakAdminService.listarUsuarios()).thenReturn(List.of(
            buildKeycloakUser("kc-admin-1", "admin@mail.com", "Admin Keycloak"),
            buildKeycloakUser("kc-cliente-1", "cliente@mail.com", "Cliente Keycloak")));
        when(usuarioService.localizarOuCriarPorKeycloak(org.mockito.ArgumentMatchers.any(UserRepresentation.class))).thenAnswer(invocation -> {
            UserRepresentation user = invocation.getArgument(0);
            if ("kc-admin-1".equals(user.getId())) {
                return buildUsuario(1L, "admin@mail.com", "kc-admin-1");
            }
            if ("kc-cliente-1".equals(user.getId())) {
                return buildUsuario(2L, "cliente@mail.com", "kc-cliente-1");
            }
            return null;
        });

        given()
                .when()
                .get("/admin/usuarios")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", is(1))
                .body("[0].email", is("admin@mail.com"))
                .body("[0].nome", is("Admin Keycloak"))
                .body("[1].id", is(2))
                .body("[1].email", is("cliente@mail.com"))
                .body("[1].nome", is("Cliente Keycloak"));
    }

    @Test
    void shouldReturnUsuarioById() {
        when(usuarioService.findById(1L)).thenReturn(buildUsuario(1L, "admin@mail.com", "kc-admin-1"));
        when(keycloakAdminService.obterUsuario("kc-admin-1")).thenReturn(buildKeycloakUser("kc-admin-1", "admin@mail.com", "Admin Keycloak"));

        given()
                .when()
                .get("/admin/usuarios/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("email", is("admin@mail.com"))
                .body("nome", is("Admin Keycloak"))
                .body("keycloakId", is("kc-admin-1"));
    }

    @Test
    void shouldDeleteUsuarioSuccessfully() {
            doNothing().when(usuarioService).delete("kc-admin-1");

        given()
                .when()
                .delete("/admin/usuarios/kc-admin-1")
                .then()
                .statusCode(204);

            verify(usuarioService).delete("kc-admin-1");
    }

    @Test
    void shouldFailDeleteWhenUsuarioNotFound() {
            doThrow(new NotFoundException("Usuário não encontrado"))
                .when(usuarioService).delete("missing-keycloak-id");

        given()
                .when()
                .delete("/admin/usuarios/missing-keycloak-id")
                .then()
                .statusCode(404)
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", is("Usuário não encontrado"))
                .body("instance", is("/admin/usuarios/missing-keycloak-id"));
    }

    @Test
    void shouldPromoteUsuarioEvenWhenItExistsOnlyInKeycloak() {
        doNothing().when(usuarioService).promoteToAdmin("kc-only-1");

        given()
                .when()
                .post("/admin/usuarios/kc-only-1/promover")
                .then()
                .statusCode(204);

        verify(usuarioService).promoteToAdmin("kc-only-1");
    }

    private Usuario buildUsuario(Long id, String email, String keycloakId) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setKeycloakId(keycloakId);
        usuario.setDataCadastro(LocalDateTime.of(2026, 5, 8, 10, 0));
        return usuario;
    }

    private UserRepresentation buildKeycloakUser(String id, String email, String firstName) {
        UserRepresentation user = new UserRepresentation();
        user.setId(id);
        user.setEmail(email);
        user.setUsername(email);
        user.setFirstName(firstName);
        return user;
    }
}
