package br.unitins.tp1.xadrez.e.comerce.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.unitins.tp1.xadrez.e.comerce.DTO.CadastroCompletoDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Endereco;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.repository.EnderecoRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.UsuarioRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class UsuarioServiceImplTest {

    @Inject
    UsuarioService usuarioService;

    @InjectMock
    UsuarioRepository usuarioRepository;

    @InjectMock
    EnderecoRepository enderecoRepository;

    @InjectMock
    JsonWebToken jwt;

    @BeforeEach
    void setUp() {
        reset(usuarioRepository, enderecoRepository, jwt);
    }

    @Test
    void shouldCreateLocalUserWhenItDoesNotExist() {
        when(jwt.getSubject()).thenReturn("kc-user-1");
        when(jwt.getClaim("email")).thenReturn("user@mail.com");
        when(jwt.getClaim("preferred_username")).thenReturn("Usuario Teste");
        when(usuarioRepository.findByKeycloakId("kc-user-1")).thenReturn(null);

        Usuario usuario = usuarioService.obterOuCriarUsuarioLocal();

        assertEquals("kc-user-1", usuario.getKeycloakId());
        assertEquals("user@mail.com", usuario.getEmail());
        assertEquals("Usuario Teste", usuario.getNome());
        assertFalse(usuario.isCadastroCompleto());
    }

    @Test
    void shouldCompleteCadastroAndPersistEndereco() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setKeycloakId("kc-user-1");

        when(jwt.getSubject()).thenReturn("kc-user-1");
        when(usuarioRepository.findByKeycloakId("kc-user-1")).thenReturn(usuario);

        @SuppressWarnings("unchecked")
        PanacheQuery<Endereco> query = (PanacheQuery<Endereco>) org.mockito.Mockito.mock(PanacheQuery.class);
        when(enderecoRepository.findByUsuarioId(1L)).thenReturn(query);
        when(query.firstResult()).thenReturn(null);

        CadastroCompletoDTO dto = new CadastroCompletoDTO(
                "Usuario Teste",
                "12345678900",
                "62999990000",
                "Rua A",
                "10",
                "Casa 1",
                "77000000",
                "Palmas",
                "TO",
                "Brasil");

        Usuario atualizado = usuarioService.completarCadastro(dto);

        assertTrue(atualizado.isCadastroCompleto());
        assertEquals("Usuario Teste", atualizado.getNome());
        assertEquals("12345678900", atualizado.getCpf());
        assertEquals("62999990000", atualizado.getTelefone());
        verify(enderecoRepository).persist(org.mockito.ArgumentMatchers.any(Endereco.class));
    }
}