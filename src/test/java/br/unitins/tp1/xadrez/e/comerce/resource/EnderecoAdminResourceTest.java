package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
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
class EnderecoAdminResourceTest {

    @InjectMock
    EnderecoService enderecoService;

    @BeforeEach
    void setUp() {
        reset(enderecoService);
    }

    @Test
    void shouldReturnAllEnderecos() {
        when(enderecoService.findAll()).thenReturn(List.of(buildEndereco(1L, 1L), buildEndereco(2L, 2L)));

        given()
                .when()
                .get("/admin/enderecos")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", is(1))
                .body("[1].id", is(2));
    }

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