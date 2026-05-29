package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
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

import br.unitins.tp1.xadrez.e.comerce.model.Material;
import br.unitins.tp1.xadrez.e.comerce.service.MaterialService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;

@QuarkusTest
@TestSecurity(user = "admin", roles = {"ADMIN"})
class MaterialResourceTest {

    @InjectMock
    MaterialService materialService;

    @BeforeEach
    void setUp() {
        reset(materialService);
    }

    @Test
    void shouldCreateValidMaterial() {
        Material created = new Material();
        created.setId(1L);
        created.setNome("Madeira");

        when(materialService.create(any(Material.class))).thenReturn(created);

        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Madeira\"}")
                .when()
                .post("/admin/material")
                .then()
                .statusCode(anyOf(is(200), is(201)))
                .body("id", equalTo(1))
                .body("nome", is("Madeira"));
    }

    @Test
    void shouldFailCreateWhenNomeIsNull() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":null}")
                .when()
                .post("/admin/material")
                .then()
                .statusCode(422)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", equalTo("Um ou mais campos não passaram na validação."))
                .body("instance", is("/admin/material"))
                .body("timestamp", notNullValue())
                .body("errors.size()", greaterThanOrEqualTo(1))
                .body("errors[0].field", notNullValue())
                .body("errors[0].message", notNullValue());
    }

    @Test
    void shouldFailCreateWhenNomeIsBlank() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"   \"}")
                .when()
                .post("/admin/material")
                .then()
                .statusCode(422)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", equalTo("Um ou mais campos não passaram na validação."))
                .body("instance", is("/admin/material"))
                .body("timestamp", notNullValue())
                .body("errors.size()", greaterThanOrEqualTo(1))
                .body("errors[0].field", notNullValue())
                .body("errors[0].message", notNullValue());
    }

    @Test
    void shouldFailCreateWhenNomeHasLessThanThreeChars() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"ab\"}")
                .when()
                .post("/admin/material")
                .then()
                .statusCode(422)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", equalTo("Um ou mais campos não passaram na validação."))
                .body("instance", is("/admin/material"))
                .body("timestamp", notNullValue())
                .body("errors.size()", greaterThanOrEqualTo(1))
                .body("errors[0].field", notNullValue())
                .body("errors[0].message", notNullValue());
    }

    @Test
    void shouldReturnAllMaterials() {
        Material m1 = new Material();
        m1.setId(1L);
        m1.setNome("Madeira");

        Material m2 = new Material();
        m2.setId(2L);
        m2.setNome("Plastico");

        when(materialService.findAll()).thenReturn(List.of(m1, m2));

        given()
                .when()
                .get("/admin/material")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].nome", is("Madeira"))
                .body("[1].id", equalTo(2))
                .body("[1].nome", is("Plastico"));
    }

    @Test
    void shouldReturnMaterialById() {
        Material material = new Material();
        material.setId(10L);
        material.setNome("Acrilico");

        when(materialService.findById(10L)).thenReturn(material);

        given()
                .when()
                .get("/admin/material/10")
                .then()
                .statusCode(200)
                .body("id", equalTo(10))
                .body("nome", is("Acrilico"));
    }

    @Test
    void shouldReturnMaterialsByTipo() {
        Material material = new Material();
        material.setId(3L);
        material.setNome("Madeira");

        when(materialService.findByTipo("madeira")).thenReturn(List.of(material));

        given()
                .when()
            .get("/admin/material/find/tipo/madeira")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].id", equalTo(3))
                .body("[0].nome", is("Madeira"));
    }


    @Test
    void shouldUpdateValidMaterial() {
        doNothing().when(materialService).update(eq(1L), any(Material.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Resina\"}")
                .when()
                .put("/admin/material/1")
                .then()
                .statusCode(204);

        verify(materialService).update(eq(1L), any(Material.class));
    }

    @Test
    void shouldFailUpdateWithInvalidData() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"x\"}")
                .when()
                .put("/admin/material/1")
                .then()
                .statusCode(422)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", equalTo("Um ou mais campos não passaram na validação."))
                .body("instance", is("/admin/material/1"))
                .body("timestamp", notNullValue())
                .body("errors.size()", greaterThanOrEqualTo(1))
                .body("errors[0].field", notNullValue())
                .body("errors[0].message", notNullValue());
    }

            @Test
            void shouldFailUpdateWhenMaterialNotFound() {
            doThrow(new NotFoundException("Material não encontrado"))
                .when(materialService).update(eq(999L), any(Material.class));

            given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Resina\"}")
                .when()
                .put("/admin/material/999")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("Material não encontrado"))
                .body("instance", is("/admin/material/999"))
                .body("timestamp", notNullValue());
            }

    @Test
    void shouldDeleteMaterialSuccessfully() {
        doNothing().when(materialService).delete(1L);

        given()
                .when()
                .delete("/admin/material/1")
                .then()
                .statusCode(204);

        verify(materialService).delete(1L);
    }

    @Test
    void shouldFailDeleteWhenMaterialNotFound() {
        doThrow(new NotFoundException("Material não encontrado"))
                .when(materialService).delete(999L);

        given()
                .when()
                .delete("/admin/material/999")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("Material não encontrado"))
                .body("instance", is("/admin/material/999"))
                .body("timestamp", notNullValue());
    }
}
