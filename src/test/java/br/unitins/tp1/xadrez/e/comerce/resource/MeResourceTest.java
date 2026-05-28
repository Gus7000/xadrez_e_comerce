package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.model.Endereco;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;

@QuarkusTest
@TestSecurity(user = "cliente@mail.com", roles = {"CLIENTE"})
class MeResourceTest {

    @InjectMock
    UsuarioService usuarioService;

    @Test
    void shouldReturnLoggedUserProfile() {
        reset(usuarioService);
        when(usuarioService.findByKeycloakId("cliente@mail.com")).thenReturn(buildUsuario());

        given()
                .when()
                .get("/me")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("email", is("cliente@mail.com"))
                .body("keycloakId", is("cliente@mail.com"))
                .body("nome", is("Usuario Teste"))
                .body("telefone", is("62999990000"))
                .body("cpf", is("12345678900"))
                .body("cadastroCompleto", is(true))
                .body("enderecos.size()", equalTo(1))
                .body("enderecos[0].rua", is("Rua Teste"))
                .body("dataCadastro", notNullValue());
    }

    private Usuario buildUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("cliente@mail.com");
        usuario.setKeycloakId("cliente@mail.com");
        usuario.setNome("Usuario Teste");
        usuario.setTelefone("62999990000");
        usuario.setCpf("12345678900");
        usuario.setCadastroCompleto(true);
        usuario.setDataCadastro(LocalDateTime.of(2026, 5, 8, 10, 0));
        Endereco endereco = new Endereco();
        endereco.setRua("Rua Teste");
        endereco.setNumero("123");
        endereco.setComplemento("Apto 1");
        endereco.setCep("74000000");
        endereco.setCidade("Cidade X");
        endereco.setEstado("Estado Y");
        endereco.setPais("Brasil");
        endereco.setUsuario(usuario);
        List<Endereco> lista = new ArrayList<>();
        lista.add(endereco);
        usuario.setEnderecos(lista);
        return usuario;
    }
}