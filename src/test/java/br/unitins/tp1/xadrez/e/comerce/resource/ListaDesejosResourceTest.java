package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.is;

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


@QuarkusTest
@TestSecurity(user = "admin", roles = { "ADMIN" })
class ListaDesejosResourceTest {

    @InjectMock
    ListaDesejosService service;

    @BeforeEach
    void setUp() {
        reset(service);
    }

    @Test
    void shouldReturnAllListas() {
        when(service.findAll()).thenReturn(List.of(buildLista(1L, 1L), buildLista(2L, 1L)));

        given()
            .when()
            .get("/admin/lista-desejos")
            .then()
            .statusCode(200)
            .body("size()", is(2))
            .body("[0].jogos.size()", is(2))
            .body("[0].jogos[0].id", is(1))
            .body("[0].jogos[0].nome", is("Jogo 1"))
            .body("[0].jogos[0].preco", is(100.0f))
            .body("[0].jogos[1].id", is(2))
            .body("[0].jogos[1].nome", is("Jogo 2"))
            .body("[0].jogos[1].preco", is(200.0f));
    }
    

    private ListaDesejos buildLista(Long id, Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setEmail("u" + usuarioId + "@mail.com");

        JogoXadrez jogo1 = new JogoXadrez();
        jogo1.setId(1L);
        jogo1.setNome("Jogo 1");
        jogo1.setPreco(100.0);
        jogo1.setDescricao("Descricao 1");
        jogo1.setEstoqueDisponivel(5);

        JogoXadrez jogo2 = new JogoXadrez();
        jogo2.setId(2L);
        jogo2.setNome("Jogo 2");
        jogo2.setPreco(200.0);
        jogo2.setDescricao("Descricao 2");
        jogo2.setEstoqueDisponivel(8);

        ListaDesejos lista = new ListaDesejos();
        lista.setId(id);
        lista.setUsuario(usuario);
        lista.setJogos(new LinkedHashSet<>(List.of(jogo1, jogo2)));
        return lista;
    }
}
