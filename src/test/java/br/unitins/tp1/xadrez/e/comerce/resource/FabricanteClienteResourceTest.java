package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.service.FabricanteService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class FabricanteClienteResourceTest {

    @InjectMock
    FabricanteService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    void shouldReturnAllFabricantes() {
        when(service.findAll()).thenReturn(List.of(buildFabricante(1L, "Fab 1"), buildFabricante(2L, "Fab 2")));

        given()
                .when()
            .get("/cliente/fabricante")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].nome", is("Fab 1"));
    }

    @Test
    void shouldReturnFabricanteById() {
        when(service.findById(1L)).thenReturn(buildFabricante(1L, "Fab 1"));

        given()
                .when()
            .get("/cliente/fabricante/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("nome", is("Fab 1"));
    }

    @Test
    void shouldReturnFabricantesByNome() {
        when(service.findByNome("Fab")).thenReturn(List.of(buildFabricante(1L, "Fab 1")));

        given()
                .when()
            .get("/cliente/fabricante/find/nome/Fab")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].nome", is("Fab 1"));
    }

    private Fabricante buildFabricante(Long id, String nome) {
        Fabricante fabricante = new Fabricante();
        fabricante.setId(id);
        fabricante.setNome(nome);
        fabricante.setCnpj("04252011000110");
        fabricante.setTelefone("(63) 99999-9999");
        return fabricante;
    }
}