package br.unitins.tp1.xadrez.e.comerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.unitins.tp1.xadrez.e.comerce.DTO.AuthRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.AuthResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Perfil;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.repository.UsuarioRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
class AuthServiceImplTest {

    @InjectMock
    UsuarioRepository usuarioRepository;

    @InjectMock
    HashService hashService;

    @InjectMock
    JwtService jwtService;

    @jakarta.inject.Inject
    AuthService authService;

    @BeforeEach
    void setUp() {
        reset(usuarioRepository, hashService, jwtService);
    }

    @Test
    void shouldLoginSuccessfully() {
        Usuario usuario = buildUsuario();
        when(usuarioRepository.findByLogin("admin")).thenReturn(usuario);
        when(hashService.verify("123456", "hash-armazenado")).thenReturn(true);
        when(jwtService.gerarToken(usuario)).thenReturn("token-gerado");

        AuthResponseDTO response = authService.login(new AuthRequestDTO("admin", "123456"));

        assertEquals("admin", response.login());
        assertEquals("token-gerado", response.token());
        assertEquals(Perfil.ADMIN, response.perfil());
    }

    @Test
    void shouldFailLoginWhenUserNotFound() {
        when(usuarioRepository.findByLogin("admin")).thenReturn(null);

        assertThrows(jakarta.ws.rs.NotAuthorizedException.class,
                () -> authService.login(new AuthRequestDTO("admin", "123456")));
    }

    @Test
    void shouldFailLoginWhenPasswordIsInvalid() {
        Usuario usuario = buildUsuario();
        when(usuarioRepository.findByLogin("admin")).thenReturn(usuario);
        when(hashService.verify(any(), any())).thenReturn(false);

        assertThrows(jakarta.ws.rs.NotAuthorizedException.class,
                () -> authService.login(new AuthRequestDTO("admin", "senha-errada")));
    }

    private Usuario buildUsuario() {
        Usuario usuario = new Usuario();
        usuario.setLogin("admin");
        usuario.setSenhaHash("hash-armazenado");
        usuario.setPerfil(Perfil.ADMIN);
        return usuario;
    }
}