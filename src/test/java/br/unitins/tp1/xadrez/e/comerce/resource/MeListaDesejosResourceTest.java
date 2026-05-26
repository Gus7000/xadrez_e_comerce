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

import br.unitins.tp1.xadrez.e.comerce.DTO.MeListaDesejosItemRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.model.ListaDesejos;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.ListaDesejosService;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestSecurity(user = "cliente", roles = { "CLIENTE" })
class MeListaDesejosResourceTest {

    @InjectMock
    ListaDesejosService listaService;

    @InjectMock
    UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        reset(listaService);
        reset(usuarioService);
    }

    @Test
    void shouldReturnMyLista() {
        when(usuarioService.findByLogin("cliente")).thenReturn(buildUsuario(1L));
        when(listaService.findOrCreateByUsuarioId(1L)).thenReturn(buildLista(1L, 1L));

        given()
                .when()
                .get("/me/lista-desejos")
                .then()
                .statusCode(200)
                .body("id", is(1));
    }

    @Test
    void shouldAddItem() {
        when(usuarioService.findByLogin("cliente")).thenReturn(buildUsuario(1L));
        doNothing().when(listaService).addItem(anyLong(), anyLong());

        given()
                .contentType(ContentType.JSON)
                .body(new MeListaDesejosItemRequestDTO(1L))
                .when()
                .post("/me/lista-desejos/itens")
                .then()
                .statusCode(201);
    }

    @Test
    void shouldRemoveItem() {
        when(usuarioService.findByLogin("cliente")).thenReturn(buildUsuario(1L));
        doNothing().when(listaService).removeItem(anyLong(), anyLong());

        given()
                .when()
                .delete("/me/lista-desejos/itens/1")
                .then()
                .statusCode(204);
    }

    private Usuario buildUsuario(Long id) {
        Usuario u = new Usuario();
        u.setId(id);
        u.setEmail("c" + id + "@mail.com");
        return u;
    }

    private ListaDesejos buildLista(Long id, Long usuarioId) {
        JogoXadrez jogo1 = new JogoXadrez();
        jogo1.setId(1L);
        ListaDesejos lista = new ListaDesejos();
        lista.setId(id);
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        lista.setUsuario(usuario);
        lista.setJogos(new LinkedHashSet<>(List.of(jogo1)));
        return lista;
    }
}
