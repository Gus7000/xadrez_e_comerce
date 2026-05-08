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

import br.unitins.tp1.xadrez.e.comerce.model.CorPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.model.ItemKit;
import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Material;
import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import br.unitins.tp1.xadrez.e.comerce.model.TipoPeca;
import br.unitins.tp1.xadrez.e.comerce.DTO.KitPecaRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.service.KitPecaService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestSecurity(user = "admin", roles = {"ADMIN"})
class KitPecaResourceTest {

    @InjectMock
    KitPecaService kitPecaService;

    @BeforeEach
    void setUp() {
        reset(kitPecaService);
    }

    @Test
    void shouldReturnAllKits() {
        when(kitPecaService.findAll()).thenReturn(List.of(buildKit(1L), buildKit(2L)));

        given()
                .when()
                .get("/kit-peca")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    void shouldCreateValidKit() {
        when(kitPecaService.create(any(KitPecaRequestDTO.class))).thenReturn(buildKit(1L));

        given()
                .contentType(ContentType.JSON)
                .body("{\"itens\":[{\"pecaId\":1,\"quantidade\":16}],\"fabricanteId\":1}")
                .when()
                .post("/kit-peca")
                .then()
                .statusCode(201)
                .body("id", equalTo(1));
    }

    @Test
    void shouldFailCreateWithInvalidData() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"itens\":null,\"fabricanteId\":1}")
                .when()
                .post("/kit-peca")
                .then()
                .statusCode(422)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", equalTo("Um ou mais campos não passaram na validação."))
                .body("instance", is("/kit-peca"))
                .body("timestamp", notNullValue())
                .body("errors.size()", greaterThanOrEqualTo(1))
                .body("errors[0].field", notNullValue())
                .body("errors[0].message", notNullValue());
    }

    @Test
    void shouldFailCreateWhenFabricanteNotFound() {
        doThrow(new jakarta.ws.rs.NotFoundException("Fabricante não encontrado"))
            .when(kitPecaService).create(any(KitPecaRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"itens\":[{\"pecaId\":1,\"quantidade\":16}],\"fabricanteId\":99}")
                .when()
                .post("/kit-peca")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("Fabricante não encontrado"))
                .body("instance", is("/kit-peca"))
                .body("timestamp", notNullValue());
    }

    @Test
    void shouldFailCreateWhenPecaNotFound() {
        doThrow(new jakarta.ws.rs.NotFoundException("Peça com ID 99 não encontrada"))
            .when(kitPecaService).create(any(KitPecaRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"itens\":[{\"pecaId\":99,\"quantidade\":16}],\"fabricanteId\":1}")
                .when()
                .post("/kit-peca")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("não encontrada"))
                .body("instance", is("/kit-peca"))
                .body("timestamp", notNullValue());
    }

    @Test
    void shouldUpdateKitSuccessfully() {
        doNothing().when(kitPecaService).update(eq(1L), any(KitPecaRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"itens\":[{\"pecaId\":1,\"quantidade\":16}],\"fabricanteId\":1}")
                .when()
                .put("/kit-peca/1")
                .then()
                .statusCode(204);

        verify(kitPecaService).update(eq(1L), any(KitPecaRequestDTO.class));
    }

    @Test
    void shouldFailUpdateWhenPecaNotFound() {
        doThrow(new jakarta.ws.rs.NotFoundException("Peça com ID 99 não encontrada"))
            .when(kitPecaService).update(eq(1L), any(KitPecaRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"itens\":[{\"pecaId\":99,\"quantidade\":16}],\"fabricanteId\":1}")
                .when()
                .put("/kit-peca/1")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("não encontrada"))
                .body("instance", is("/kit-peca/1"))
                .body("timestamp", notNullValue());
    }

    @Test
    void shouldDeleteKitSuccessfully() {
        doNothing().when(kitPecaService).delete(1L);

        given()
                .when()
                .delete("/kit-peca/1")
                .then()
                .statusCode(204);

        verify(kitPecaService).delete(1L);
    }

    private KitPeca buildKit(Long id) {
        KitPeca kit = new KitPeca();
        kit.setId(id);
        kit.setFabricante(buildFabricante(1L));

        ItemKit item = new ItemKit();
        item.setId(1L);
        item.setKit(kit);
        item.setPeca(buildPeca(1L));
        item.setQuantidade(16);

        kit.setItens(List.of(item));
        return kit;
    }

    private Peca buildPeca(Long id) {
        Peca peca = new Peca();
        peca.setId(id);
        peca.setCor(CorPeca.PRETO);
        peca.setTipo(TipoPeca.REI);

        Material material = new Material();
        material.setId(1L);
        material.setNome("Madeira");
        peca.setMaterial(material);

        peca.setDiametroCm(3.2);
        peca.setAlturaCm(4.1);
        return peca;
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
