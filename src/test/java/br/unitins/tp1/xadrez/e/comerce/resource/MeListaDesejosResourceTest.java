package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.LinkedHashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.model.ListaDesejos;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.ListaDesejosService;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;


@QuarkusTest
@TestSecurity(user = "cliente", roles = { "CLIENTE" })
class MeListaDesejosResourceTest {

    @InjectMock
    ListaDesejosService listaService;

    @BeforeEach
    void setUp() {
        reset(listaService);
    }

    @Test
    void shouldReturnMyLista() {
        when(listaService.findMyLista()).thenReturn(buildLista(1L, 1L));

        given()
                .when()
                .get("/me/lista-desejos")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("jogos.size()", is(1))
                .body("jogos[0].id", is(1))
                .body("jogos[0].nome", is("Jogo 1"))
                .body("jogos[0].preco", Matchers.comparesEqualTo(100.0f));
    }

    @Test
    void shouldAddItem() {
        doNothing().when(listaService).addItem(anyLong());

        given()
                .contentType(ContentType.JSON)
                .when()
            .post("/me/lista-desejos/1")
                .then()
                .statusCode(201);
    }

    @Test
    void shouldRemoveItem() {
        doNothing().when(listaService).removeItem(anyLong());

        given()
                .when()
            .delete("/me/lista-desejos/1")
                .then()
                .statusCode(204);
    }

   

    private ListaDesejos buildLista(Long id, Long usuarioId) {
        JogoXadrez jogo1 = new JogoXadrez();
        jogo1.setId(1L);
        jogo1.setNome("Jogo 1");
        jogo1.setPreco(100.0);
        jogo1.setDescricao("Descricao 1");
        jogo1.setEstoqueDisponivel(5);
        ListaDesejos lista = new ListaDesejos();
        lista.setId(id);
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        lista.setUsuario(usuario);
        lista.setJogos(new LinkedHashSet<>(List.of(jogo1)));
        return lista;
    }
}
