package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

@QuarkusTest
@TestSecurity(user = "cliente@mail.com", roles = {"CLIENTE"})
class MeHistoricoResourceTest {

    @InjectMock
    UsuarioService usuarioService;

    @Test
    void shouldReturnPedidosHistorico() {
        reset(usuarioService);

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("cliente@mail.com");
        usuario.setKeycloakId("cliente@mail.com");

        Pedido pedido = new Pedido();
        pedido.setId(10L);
        pedido.setUsuario(usuario);
        pedido.setValorTotal(null);
        pedido.setDataCadastro(LocalDateTime.of(2026,5,8,10,0));

        List<Pedido> lista = new ArrayList<>();
        lista.add(pedido);
        usuario.setPedidos(lista);

        when(usuarioService.obterOuCriarUsuarioLocal()).thenReturn(usuario);

        given()
            .when()
            .get("/me/historico")
            .then()
            .statusCode(200)
            .body("size()", equalTo(1))
            .body("[0].id", equalTo(10));
    }
}
