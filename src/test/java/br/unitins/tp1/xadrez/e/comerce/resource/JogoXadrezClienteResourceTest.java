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
import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;
import br.unitins.tp1.xadrez.e.comerce.model.TipoPeca;
import br.unitins.tp1.xadrez.e.comerce.service.JogoXadrezService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class JogoXadrezClienteResourceTest {

    @InjectMock
    JogoXadrezService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    void shouldReturnAllJogos() {
        when(service.findAll()).thenReturn(List.of(buildJogo(1L), buildJogo(2L)));

        given()
                .when()
                .get("/jogos-xadrez")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", equalTo(1));
    }

    @Test
    void shouldReturnJogoById() {
        when(service.findById(1L)).thenReturn(buildJogo(1L));

        given()
                .when()
                .get("/jogos-xadrez/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("kitPeca.itens.size()", is(1))
                .body("tabuleiro.id", equalTo(1));
    }

    @Test
    void shouldReturnJogosByKitPeca() {
        when(service.findByKitPeca(1L)).thenReturn(List.of(buildJogo(1L)));

        given()
                .when()
                .get("/jogos-xadrez?kitPecaId=1")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].id", equalTo(1));
    }

    @Test
    void shouldReturnJogosByTabuleiro() {
        when(service.findByTabuleiro(1L)).thenReturn(List.of(buildJogo(1L)));

        given()
                .when()
                .get("/jogos-xadrez?tabuleiroId=1")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].id", equalTo(1));
    }

    private JogoXadrez buildJogo(Long id) {
        JogoXadrez jogo = new JogoXadrez();
        jogo.setId(id);
        jogo.setNome("Jogo " + id);
        jogo.setPreco(250.0);
        jogo.setDescricao("Descricao " + id);
        jogo.setKitPeca(buildKit(1L));
        jogo.setTabuleiro(buildTabuleiro(1L));
        return jogo;
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

    private Tabuleiro buildTabuleiro(Long id) {
        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setId(id);
        tabuleiro.setTamanho("40x40");
        return tabuleiro;
    }

    private Peca buildPeca(Long id) {
        Peca peca = new Peca();
        peca.setId(id);
        peca.setCor(CorPeca.BRANCO);
        peca.setTipo(TipoPeca.RAINHA);
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