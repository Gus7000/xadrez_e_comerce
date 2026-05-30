package br.unitins.tp1.xadrez.e.comerce.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.unitins.tp1.xadrez.e.comerce.model.Endereco;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.repository.EnderecoRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
class EnderecoServiceImplTest {

    @Inject
    EnderecoService enderecoService;

    @InjectMock
    EnderecoRepository enderecoRepository;

    @InjectMock
    UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        reset(enderecoRepository, usuarioService);
    }

    @Test
    void shouldResetCadastroCompletoWhenLastEnderecoIsDeleted() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCadastroCompleto(true);

        Endereco endereco = new Endereco();
        endereco.setId(10L);
        endereco.setUsuario(usuario);

        when(usuarioService.obterOuCriarUsuarioLocal()).thenReturn(usuario);
        when(enderecoRepository.findById(10L)).thenReturn(endereco);
        when(enderecoRepository.count("usuario.id = ?1", 1L)).thenReturn(0L);

        enderecoService.delete(10L);

        assertFalse(usuario.isCadastroCompleto());
        verify(enderecoRepository).delete(endereco);
    }

    @Test
    void shouldKeepCadastroCompletoWhenUserStillHasAddresses() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setCadastroCompleto(true);

        Endereco endereco = new Endereco();
        endereco.setId(10L);
        endereco.setUsuario(usuario);

        when(usuarioService.obterOuCriarUsuarioLocal()).thenReturn(usuario);
        when(enderecoRepository.findById(10L)).thenReturn(endereco);
        when(enderecoRepository.count("usuario.id = ?1", 1L)).thenReturn(1L);

        enderecoService.delete(10L);

        verify(enderecoRepository).delete(endereco);
    }

    @Test
    void shouldRejectDeleteWhenEnderecoBelongsToAnotherUser() {
        Usuario usuarioLogado = new Usuario();
        usuarioLogado.setId(1L);

        Usuario outroUsuario = new Usuario();
        outroUsuario.setId(2L);

        Endereco endereco = new Endereco();
        endereco.setId(10L);
        endereco.setUsuario(outroUsuario);

        when(usuarioService.obterOuCriarUsuarioLocal()).thenReturn(usuarioLogado);
        when(enderecoRepository.findById(10L)).thenReturn(endereco);

        org.junit.jupiter.api.Assertions.assertThrows(NotFoundException.class, () -> enderecoService.delete(10L));
    }
}