package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Endereco;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.EnderecoService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import jakarta.ws.rs.NotFoundException;

@QuarkusTest
@TestSecurity(user = "cliente", roles = { "CLIENTE" })
class MeEnderecoResourceTest {

    @InjectMock
    EnderecoService enderecoService;

    @BeforeEach
    void setUp() {
        reset(enderecoService);
    }

    @Test
    void shouldReturnMyEnderecos() {
        when(enderecoService.findMyEnderecos()).thenReturn(List.of(buildEndereco(1L, 1L)));

        given()
                .when()
                .get("/me/enderecos")
                .then()
                .statusCode(200)
                .body("size()", is(1))
                .body("[0].id", equalTo(1));
    }

    @Test
    void shouldCreateEnderecoSuccessfully() {
        when(enderecoService.create(any(EnderecoRequestDTO.class))).thenReturn(buildEndereco(1L, 1L));

        given()
                .contentType(ContentType.JSON)
                .body("{\"rua\":\"Rua A\",\"numero\":\"10\",\"complemento\":\"Apto 1\",\"cep\":\"77000000\",\"cidade\":\"Palmas\",\"estado\":\"TO\",\"pais\":\"Brasil\"}")
                .when()
                .post("/me/enderecos")
                .then()
                .statusCode(201)
                .body("id", equalTo(1))
                .body("cidade", is("Palmas"));
    }

    @Test
    void shouldFailCreateWhenPayloadIsInvalid() {
        given()
                .contentType(ContentType.JSON)
                .body("{\"rua\":\"\",\"numero\":\"\",\"cep\":\"\",\"cidade\":\"\",\"estado\":\"\",\"pais\":\"\"}")
                .when()
                .post("/me/enderecos")
                .then()
                .statusCode(422)
                .body("type", notNullValue())
                .body("title", is("Validation Error"))
                .body("status", is(422))
                .body("detail", is("Um ou mais campos não passaram na validação."))
                .body("instance", is("/me/enderecos"))
                .body("errors.size()", greaterThanOrEqualTo(1));
    }

    @Test
    void shouldUpdateEnderecoSuccessfully() {
        doNothing().when(enderecoService).update(eq(1L), any(EnderecoRequestDTO.class));

        given()
                .contentType(ContentType.JSON)
                .body("{\"rua\":\"Rua B\",\"numero\":\"20\",\"complemento\":\"Casa\",\"cep\":\"77001000\",\"cidade\":\"Palmas\",\"estado\":\"TO\",\"pais\":\"Brasil\"}")
                .when()
                .put("/me/enderecos/1")
                .then()
                .statusCode(204);
    }

    @Test
    void shouldFailDeleteWhenEnderecoNotFound() {
        doThrow(new NotFoundException("Endereço não encontrado")).when(enderecoService).delete(999L);

        given()
                .when()
                .delete("/me/enderecos/999")
                .then()
                .statusCode(404)
                .body("title", is("Not Found"))
                .body("status", is(404))
                .body("detail", is("Endereço não encontrado"))
                .body("instance", is("/me/enderecos/999"));
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
