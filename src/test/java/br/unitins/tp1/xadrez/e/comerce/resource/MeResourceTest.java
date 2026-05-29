package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.MeResponseDTO;
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
        when(usuarioService.obterMeuPerfil()).thenReturn(buildPerfil());

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

    private MeResponseDTO buildPerfil() {
        EnderecoResponseDTO endereco = new EnderecoResponseDTO(
                1L,
                "Rua Teste",
                "123",
                "Apto 1",
                "74000000",
                "Cidade X",
                "Estado Y",
            "Brasil",
            LocalDateTime.of(2026, 5, 8, 10, 0));

        return new MeResponseDTO(
                1L,
                "cliente@mail.com",
                "cliente@mail.com",
                "Usuario Teste",
                "62999990000",
                "12345678900",
                true,
                List.of(endereco),
                LocalDateTime.of(2026, 5, 8, 10, 0));
    }
}