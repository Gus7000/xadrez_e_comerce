package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.model.CorPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Material;
import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import br.unitins.tp1.xadrez.e.comerce.model.TipoPeca;
import br.unitins.tp1.xadrez.e.comerce.service.PecaService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class PecaClienteResourceTest {

    @InjectMock
    PecaService pecaService;

    @BeforeEach
    void setUp() {
        reset(pecaService);
    }

    @Test
    void shouldReturnAllPecasForCliente() {
        when(pecaService.findAll()).thenReturn(List.of(buildPeca(1L)));

        given()
                .when()
            .get("/cliente/peca")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].id", equalTo(1))
                .body("$", not(containsString("dataCadastro")));
    }

    @Test
    void shouldReturnPecaByIdForCliente() {
        when(pecaService.findById(1L)).thenReturn(buildPeca(1L));

        given()
                .when()
            .get("/cliente/peca/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("cor", is("Preto"))
                .body("$", not(containsString("dataCadastro")));
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