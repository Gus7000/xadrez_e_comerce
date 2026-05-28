package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.model.CorPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.model.ItemKit;
import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import br.unitins.tp1.xadrez.e.comerce.model.TipoPeca;
import br.unitins.tp1.xadrez.e.comerce.service.KitPecaService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class KitPecaClienteResourceTest {

    @InjectMock
    KitPecaService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    void shouldReturnAllKits() {
        when(service.findAll()).thenReturn(List.of(buildKit(1L), buildKit(2L)));

        given()
                .when()
                .get("/cliente/kit-peca")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", equalTo(1))
                .body("[0].itens.size()", is(1));
    }

    @Test
    void shouldReturnKitById() {
        when(service.findById(1L)).thenReturn(buildKit(1L));

        given()
                .when()
                .get("/cliente/kit-peca/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("itens.size()", is(1));
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