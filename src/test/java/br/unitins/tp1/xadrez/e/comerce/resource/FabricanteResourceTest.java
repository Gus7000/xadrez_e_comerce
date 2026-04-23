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

import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.service.FabricanteService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;

@QuarkusTest
class FabricanteResourceTest {

    @InjectMock
    FabricanteService fabricanteService;

    @BeforeEach
    void setUp() {
        reset(fabricanteService);
    }

    @Test
    void shouldCreateValidFabricante() {
        Fabricante created = new Fabricante();
        created.setId(1L);
        created.setNome("Unitins Chess");
        created.setCnpj("04252011000110");
        created.setTelefone("(63) 99999-9999");

        when(fabricanteService.create(any(Fabricante.class))).thenReturn(created);

        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Unitins Chess\",\"cnpj\":\"04252011000110\",\"telefone\":\"(63) 99999-9999\"}")
                .when()
                .post("/fabricante")
                .then()
                .statusCode(201)
                .body("id", equalTo(1))
                .body("nome", is("Unitins Chess"));
    }

    @Test
    void shouldFailCreateWhenNomeIsBlank() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"\",\"cnpj\":\"04252011000110\",\"telefone\":\"(63) 99999-9999\"}")
                .when()
                .post("/fabricante")
                .then()
                .statusCode(422)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", equalTo("Um ou mais campos não passaram na validação."))
                .body("instance", is("/fabricante"))
                .body("timestamp", notNullValue())
                .body("errors.size()", greaterThanOrEqualTo(1))
                .body("errors[0].field", notNullValue())
                .body("errors[0].message", notNullValue());
    }

    @Test
    void shouldReturnAllFabricantes() {
        Fabricante f1 = new Fabricante();
        f1.setId(1L);
        f1.setNome("Unitins");
        f1.setCnpj("04252011000110");
        f1.setTelefone("(63) 99999-9999");

        Fabricante f2 = new Fabricante();
        f2.setId(2L);
        f2.setNome("Chess BR");
        f2.setCnpj("19131243000197");
        f2.setTelefone("(11) 98888-7777");

        when(fabricanteService.findAll()).thenReturn(List.of(f1, f2));

        given()
                .when()
                .get("/fabricante")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].nome", is("Unitins"))
                .body("[1].nome", is("Chess BR"));
    }

    @Test
    void shouldReturnFabricanteById() {
        Fabricante fabricante = new Fabricante();
        fabricante.setId(11L);
        fabricante.setNome("Chess LTDA");
        fabricante.setCnpj("04252011000110");
        fabricante.setTelefone("(63) 99999-9999");

        when(fabricanteService.findById(11L)).thenReturn(fabricante);

        given()
                .when()
                .get("/fabricante/11")
                .then()
                .statusCode(200)
                .body("id", equalTo(11))
                .body("nome", is("Chess LTDA"));
    }

    @Test
    void shouldReturnFabricantesByNome() {
        Fabricante fabricante = new Fabricante();
        fabricante.setId(20L);
        fabricante.setNome("Unitins");
        fabricante.setCnpj("04252011000110");
        fabricante.setTelefone("(63) 99999-9999");

        when(fabricanteService.findByNome("Unitins")).thenReturn(List.of(fabricante));

        given()
                .when()
                .get("/fabricante/find/nome/Unitins")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].id", equalTo(20))
                .body("[0].nome", is("Unitins"));
    }


    @Test
    void shouldReturnFabricanteByCnpj() {
        Fabricante fabricante = new Fabricante();
        fabricante.setId(21L);
        fabricante.setNome("Unitins");
        fabricante.setCnpj("04252011000110");
        fabricante.setTelefone("(63) 99999-9999");

        when(fabricanteService.findByCnpj("04252011000110")).thenReturn(fabricante);

        given()
                .when()
                .get("/fabricante/find/cnpj/04252011000110")
                .then()
                .statusCode(200)
                .body("id", equalTo(21))
                .body("cnpj", is("04252011000110"));
    }

    @Test
    void shouldUpdateFabricanteSuccessfully() {
        doNothing().when(fabricanteService).update(eq(1L), any(Fabricante.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Novo Nome\",\"cnpj\":\"04252011000110\",\"telefone\":\"(63) 99999-9999\"}")
                .when()
                .put("/fabricante/1")
                .then()
                .statusCode(204);

        verify(fabricanteService).update(eq(1L), any(Fabricante.class));
    }

    @Test
    void shouldFailUpdateWithInvalidTelefone() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Novo Nome\",\"cnpj\":\"04252011000110\",\"telefone\":\"63999999999\"}")
                .when()
                .put("/fabricante/1")
                .then()
                .statusCode(422)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", equalTo("Um ou mais campos não passaram na validação."))
                .body("instance", is("/fabricante/1"))
                .body("timestamp", notNullValue())
                .body("errors.size()", greaterThanOrEqualTo(1))
                .body("errors[0].field", notNullValue())
                .body("errors[0].message", notNullValue());
    }

            @Test
            void shouldFailUpdateWhenFabricanteNotFound() {
            doThrow(new NotFoundException("Fabricante não encontrado"))
                .when(fabricanteService).update(eq(999L), any(Fabricante.class));

            given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Novo Nome\",\"cnpj\":\"04252011000110\",\"telefone\":\"(63) 99999-9999\"}")
                .when()
                .put("/fabricante/999")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("Fabricante não encontrado"))
                .body("instance", is("/fabricante/999"))
                .body("timestamp", notNullValue());
            }

    @Test
    void shouldDeleteFabricanteSuccessfully() {
        doNothing().when(fabricanteService).delete(1L);

        given()
                .when()
                .delete("/fabricante/1")
                .then()
                .statusCode(204);

        verify(fabricanteService).delete(1L);
    }

    @Test
    void shouldFailDeleteWhenFabricanteNotFound() {
        doThrow(new NotFoundException("Fabricante não encontrado"))
                .when(fabricanteService).delete(999L);

        given()
                .when()
                .delete("/fabricante/999")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("Fabricante não encontrado"))
                .body("instance", is("/fabricante/999"))
                .body("timestamp", notNullValue());
    }
}
