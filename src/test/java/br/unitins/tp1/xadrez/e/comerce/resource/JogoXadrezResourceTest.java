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

import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.CorPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.model.ItemKit;
import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Material;
import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;
import br.unitins.tp1.xadrez.e.comerce.model.TipoPeca;
import br.unitins.tp1.xadrez.e.comerce.service.JogoXadrezService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestSecurity(user = "admin", roles = {"ADMIN"})
class JogoXadrezResourceTest {

    @InjectMock
    JogoXadrezService jogoXadrezService;

    @BeforeEach
    void setUp() {
        reset(jogoXadrezService);
    }

    @Test
    void shouldReturnAllJogos() {
        when(jogoXadrezService.findAll()).thenReturn(List.of(buildJogo(1L), buildJogo(2L)));

        given()
                .when()
                .get("/admin/jogos-xadrez")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }

  
    @Test
    void shouldCreateValidJogo() {
        when(jogoXadrezService.create(any(JogoXadrezRequestDTO.class))).thenReturn(buildJogo(1L));

        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Jogo Premium\",\"preco\":199.9,\"descricao\":\"Conjunto completo\",\"estoqueDisponivel\":10,\"kitPecaId\":1,\"tabuleiroId\":1}")
                .when()
                .post("/admin/jogos-xadrez")
                .then()
                .statusCode(201)
                .body("id", equalTo(1));
    }

    @Test
    void shouldFailCreateWithInvalidData() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"\",\"preco\":199.9,\"descricao\":\"Conjunto completo\",\"estoqueDisponivel\":10,\"kitPecaId\":1,\"tabuleiroId\":1}")
                .when()
                .post("/admin/jogos-xadrez")
                .then()
                .statusCode(422)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", equalTo("Um ou mais campos não passaram na validação."))
                .body("instance", is("/admin/jogos-xadrez"))
                .body("timestamp", notNullValue())
                .body("errors.size()", greaterThanOrEqualTo(1))
                .body("errors[0].field", notNullValue())
                .body("errors[0].message", notNullValue());
    }

    @Test
    void shouldFailCreateWhenKitPecaNotFound() {
        doThrow(new jakarta.ws.rs.NotFoundException("Kit de Peças não encontrado"))
            .when(jogoXadrezService).create(any(JogoXadrezRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Jogo Premium\",\"preco\":199.9,\"descricao\":\"Conjunto completo\",\"estoqueDisponivel\":10,\"kitPecaId\":99,\"tabuleiroId\":1}")
                .when()
                .post("/admin/jogos-xadrez")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("Kit de Peças não encontrado"))
                .body("instance", is("/admin/jogos-xadrez"))
                .body("timestamp", notNullValue());
    }

    @Test
    void shouldUpdateJogoSuccessfully() {
        doNothing().when(jogoXadrezService).update(eq(1L), any(JogoXadrezRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Jogo Premium\",\"preco\":199.9,\"descricao\":\"Conjunto completo\",\"estoqueDisponivel\":10,\"kitPecaId\":1,\"tabuleiroId\":1}")
                .when()
                .put("/admin/jogos-xadrez/1")
                .then()
                .statusCode(204);

        verify(jogoXadrezService).update(eq(1L), any(JogoXadrezRequestDTO.class));
    }

    @Test
    void shouldFailUpdateWhenTabuleiroNotFound() {
        doThrow(new jakarta.ws.rs.NotFoundException("Tabuleiro não encontrado"))
            .when(jogoXadrezService).update(eq(1L), any(JogoXadrezRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Jogo Premium\",\"preco\":199.9,\"descricao\":\"Conjunto completo\",\"estoqueDisponivel\":10,\"kitPecaId\":1,\"tabuleiroId\":99}")
                .when()
                .put("/admin/jogos-xadrez/1")
                .then()
                .statusCode(404)
                .contentType("application/problem+json")
                .body("type", notNullValue())
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", containsString("Tabuleiro não encontrado"))
                .body("instance", is("/admin/jogos-xadrez/1"))
                .body("timestamp", notNullValue());
    }

    @Test
    void shouldDeleteJogoSuccessfully() {
        doNothing().when(jogoXadrezService).delete(1L);

        given()
                .when()
                .delete("/admin/jogos-xadrez/1")
                .then()
                .statusCode(204);

        verify(jogoXadrezService).delete(1L);
    }

    private JogoXadrez buildJogo(Long id) {
        JogoXadrez jogo = new JogoXadrez();
        jogo.setId(id);
        jogo.setNome("Jogo");
        jogo.setPreco(100.0);
        jogo.setDescricao("Desc");
        jogo.setEstoqueDisponivel(5);
        jogo.setKitPeca(buildKit(1L));
        jogo.setTabuleiro(buildTabuleiro(1L));
        return jogo;
    }

    private KitPeca buildKit(Long id) {
        KitPeca kit = new KitPeca();
        kit.setId(id);

        Fabricante fabricante = new Fabricante();
        fabricante.setId(1L);
        fabricante.setNome("Fab");
        fabricante.setCnpj("04252011000110");
        fabricante.setTelefone("(63) 99999-9999");
        kit.setFabricante(fabricante);

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
        tabuleiro.setTamanho("8x8");

        Material material = new Material();
        material.setId(1L);
        material.setNome("Madeira");
        tabuleiro.setMaterial(material);

        Fabricante fabricante = new Fabricante();
        fabricante.setId(1L);
        fabricante.setNome("Fab");
        fabricante.setCnpj("04252011000110");
        fabricante.setTelefone("(63) 99999-9999");
        tabuleiro.setFabricante(fabricante);

        return tabuleiro;
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
}
