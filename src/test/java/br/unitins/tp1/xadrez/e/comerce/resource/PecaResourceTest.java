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

import br.unitins.tp1.xadrez.e.comerce.DTO.PecaRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.CorPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Material;
import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import br.unitins.tp1.xadrez.e.comerce.model.TipoPeca;
import br.unitins.tp1.xadrez.e.comerce.service.PecaService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestSecurity(user = "admin", roles = {"ADMIN"})
class PecaResourceTest {

    @InjectMock
    PecaService pecaService;

    @BeforeEach
    void setUp() {
        reset(pecaService);
    }

    @Test
    void shouldReturnAllPecas() {
        when(pecaService.findAll()).thenReturn(List.of(buildPeca(1L), buildPeca(2L)));

        given()
                .when()
            .get("/admin/peca")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }


    @Test
    void shouldCreateValidPeca() {
        when(pecaService.create(any(PecaRequestDTO.class))).thenReturn(buildPeca(1L));

        given()
                .contentType(ContentType.JSON)
                .body("{\"corId\":1,\"tipoId\":1,\"materialId\":1,\"diametroCm\":3.2,\"alturaCm\":4.1}")
                .when()
            .post("/admin/peca")
                .then()
                .statusCode(201)
                .body("id", equalTo(1));
    }

    @Test
    void shouldFailCreateWithInvalidData() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"corId\":1,\"tipoId\":1,\"materialId\":1,\"diametroCm\":-1,\"alturaCm\":4.1}")
                .when()
            .post("/admin/peca")
                .then()
                .statusCode(422)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", equalTo("Um ou mais campos não passaram na validação."))
            .body("instance", is("/admin/peca"))
                .body("timestamp", notNullValue())
                .body("errors.size()", greaterThanOrEqualTo(1))
                .body("errors[0].field", notNullValue())
                .body("errors[0].message", notNullValue());
    }

    @Test
    void shouldFailCreateWhenMaterialNotFound() {
        doThrow(new jakarta.ws.rs.NotFoundException("Material não encontrado"))
            .when(pecaService).create(any(PecaRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"corId\":1,\"tipoId\":1,\"materialId\":99,\"diametroCm\":3.2,\"alturaCm\":4.1}")
                .when()
            .post("/admin/peca")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("Material não encontrado"))
            .body("instance", is("/admin/peca"))
                .body("timestamp", notNullValue());
    }

    @Test
    void shouldUpdatePecaSuccessfully() {
        doNothing().when(pecaService).update(eq(1L), any(PecaRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"corId\":1,\"tipoId\":1,\"materialId\":1,\"diametroCm\":3.2,\"alturaCm\":4.1}")
                .when()
            .put("/admin/peca/1")
                .then()
                .statusCode(204);

        verify(pecaService).update(eq(1L), any(PecaRequestDTO.class));
    }

    @Test
    void shouldFailUpdateWhenMaterialNotFound() {
        doThrow(new jakarta.ws.rs.NotFoundException("Material não encontrado"))
            .when(pecaService).update(eq(1L), any(PecaRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"corId\":1,\"tipoId\":1,\"materialId\":99,\"diametroCm\":3.2,\"alturaCm\":4.1}")
                .when()
            .put("/admin/peca/1")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("Material não encontrado"))
            .body("instance", is("/admin/peca/1"))
                .body("timestamp", notNullValue());
    }

    @Test
    void shouldDeletePecaSuccessfully() {
        doNothing().when(pecaService).delete(1L);

        given()
                .when()
            .delete("/admin/peca/1")
                .then()
                .statusCode(204);

        verify(pecaService).delete(1L);
    }

    private Peca buildPeca(Long id) {
        Peca peca = new Peca();
        peca.setId(id);
        peca.setCor(CorPeca.PRETO);
        peca.setTipo(TipoPeca.REI);
        peca.setMaterial(buildMaterial(1L));
        peca.setDiametroCm(3.2);
        peca.setAlturaCm(4.1);
        return peca;
    }

    private Material buildMaterial(Long id) {
        Material material = new Material();
        material.setId(id);
        material.setNome("Madeira");
        return material;
    }
}
