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

import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.model.Material;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;
import br.unitins.tp1.xadrez.e.comerce.service.TabuleiroService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestSecurity(user = "admin", roles = {"ADMIN"})
class TabuleiroResourceTest {

    @InjectMock
    TabuleiroService tabuleiroService;

    @BeforeEach
    void setUp() {
        reset(tabuleiroService);
    }

    @Test
    void shouldReturnAllTabuleiros() {
        when(tabuleiroService.findAll()).thenReturn(List.of(buildTabuleiro(1L), buildTabuleiro(2L)));

        given()
                .when()
            .get("/admin/tabuleiro")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }


    @Test
    void shouldCreateValidTabuleiro() {
        when(tabuleiroService.create(any(TabuleiroRequestDTO.class))).thenReturn(buildTabuleiro(1L));

        given()
                .contentType(ContentType.JSON)
                .body("{\"tamanho\":\"8x8\",\"materialId\":1,\"fabricanteId\":1}")
                .when()
            .post("/admin/tabuleiro")
                .then()
                .statusCode(201)
                .body("id", equalTo(1));
    }

    @Test
    void shouldFailCreateWithInvalidData() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"tamanho\":\"\",\"materialId\":1,\"fabricanteId\":1}")
                .when()
            .post("/admin/tabuleiro")
                .then()
                .statusCode(422)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", equalTo("Um ou mais campos não passaram na validação."))
            .body("instance", is("/admin/tabuleiro"))
                .body("timestamp", notNullValue())
                .body("errors.size()", greaterThanOrEqualTo(1))
                .body("errors[0].field", notNullValue())
                .body("errors[0].message", notNullValue());
    }

    @Test
    void shouldFailCreateWhenMaterialNotFound() {
        doThrow(new jakarta.ws.rs.NotFoundException("Material não encontrado"))
            .when(tabuleiroService).create(any(TabuleiroRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"tamanho\":\"8x8\",\"materialId\":99,\"fabricanteId\":1}")
                .when()
            .post("/admin/tabuleiro")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("Material não encontrado"))
            .body("instance", is("/admin/tabuleiro"))
                .body("timestamp", notNullValue());
    }

    @Test
    void shouldUpdateTabuleiroSuccessfully() {
        doNothing().when(tabuleiroService).update(eq(1L), any(TabuleiroRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"tamanho\":\"8x8\",\"materialId\":1,\"fabricanteId\":1}")
                .when()
            .put("/admin/tabuleiro/1")
                .then()
                .statusCode(204);

        verify(tabuleiroService).update(eq(1L), any(TabuleiroRequestDTO.class));
    }

    @Test
    void shouldFailUpdateWhenFabricanteNotFound() {
        doThrow(new jakarta.ws.rs.NotFoundException("Fabricante não encontrado"))
            .when(tabuleiroService).update(eq(1L), any(TabuleiroRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"tamanho\":\"8x8\",\"materialId\":1,\"fabricanteId\":99}")
                .when()
            .put("/admin/tabuleiro/1")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("Fabricante não encontrado"))
            .body("instance", is("/admin/tabuleiro/1"))
                .body("timestamp", notNullValue());
    }

    @Test
    void shouldDeleteTabuleiroSuccessfully() {
        doNothing().when(tabuleiroService).delete(1L);

        given()
                .when()
            .delete("/admin/tabuleiro/1")
                .then()
                .statusCode(204);

        verify(tabuleiroService).delete(1L);
    }

    private Tabuleiro buildTabuleiro(Long id) {
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setId(id);
        tabuleiro.setTamanho("8x8");
        tabuleiro.setMaterial(buildMaterial(1L));
        tabuleiro.setFabricante(buildFabricante(1L));
        return tabuleiro;
    }

    private Material buildMaterial(Long id) {
        Material material = new Material();
        material.setId(id);
        material.setNome("Madeira");
        return material;
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
