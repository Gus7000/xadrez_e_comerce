package br.unitins.tp1.xadrez.e.comerce.resource;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioPerfilUpdateDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.service.UsuarioService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@QuarkusTest
@TestSecurity(user = "cliente@mail.com", roles = { "CLIENTE" })
class UsuarioCadastroResourceTest {

    @InjectMock
    UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        reset(usuarioService);
    }

    @Test
    void shouldCompleteCadastroSuccessfully() {
        when(usuarioService.atualizarPerfil(any(UsuarioPerfilUpdateDTO.class))).thenReturn(buildUsuario());

        given()
                .contentType(ContentType.JSON)
                .body("{\"nome\":\"Usuario Teste\",\"cpf\":\"12345678900\",\"telefone\":\"62999990000\"}")
                .when()
                .put("/me/perfil")
                .then()
                .statusCode(200);

        verify(usuarioService).atualizarPerfil(any(UsuarioPerfilUpdateDTO.class));
    }

    private Usuario buildUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("cliente@mail.com");
        usuario.setKeycloakId("kc-1");
        usuario.setCadastroCompleto(false);
        return usuario;
    }
}