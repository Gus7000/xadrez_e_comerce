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
        when(service.findAll()).thenReturn(List.of(buildLista(1L, 1L), buildLista(2L, 2L)));

        given()
                .when()
                .get("/admin/lista-desejos")
                .then()
                .statusCode(200)
                .body("size()", is(2));
    }
    

    private ListaDesejos buildLista(Long id, Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setEmail("u" + usuarioId + "@mail.com");

        JogoXadrez jogo1 = new JogoXadrez();
        jogo1.setId(1L);

        JogoXadrez jogo2 = new JogoXadrez();
        jogo2.setId(2L);

        ListaDesejos lista = new ListaDesejos();
        lista.setId(id);
        lista.setUsuario(usuario);
        lista.setJogos(new LinkedHashSet<>(List.of(jogo1, jogo2)));
        return lista;
    }
}
