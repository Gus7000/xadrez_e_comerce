package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.reset;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import br.unitins.tp1.xadrez.e.comerce.model.Endereco;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.EnderecoService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;


@QuarkusTest
@TestSecurity(user = "admin", roles = { "ADMIN" })
class EnderecoResourceTest {

    @InjectMock
    EnderecoService enderecoService;

    @BeforeEach
    void setUp() {
        reset(enderecoService);
    }

    @Test
    void shouldReturnAllEnderecos() {
        when(enderecoService.findByUsuarioId(1L)).thenReturn(List.of(buildEndereco(1L, 1L), buildEndereco(2L, 1L)));

        given()
            .when()
            .get("/admin/utilizadores/1/enderecos")
            .then()
            .statusCode(200)
            .body("size()", is(2))
            .body("[0].id", equalTo(1))
            .body("[1].id", equalTo(2));
    }

    // Other admin endpoints were refactored. Admin now exposes listing of a user's enderecos

    // Note: admin delete endpoint was removed in refactor; deletion is handled via /me/enderecos by the owner.

    private Endereco buildEndereco(Long id, Long usuarioId) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setEmail("u" + usuarioId + "@mail.com");

        Endereco endereco = new Endereco();
        endereco.setId(id);
        endereco.setRua("Rua A");
        endereco.setNumero("10");
        endereco.setComplemento("Apto 1");
        endereco.setCep("77000000");
        endereco.setCidade("Palmas");
        endereco.setEstado("TO");
        endereco.setPais("Brasil");
        endereco.setUsuario(usuario);
        return endereco;
    }
}
