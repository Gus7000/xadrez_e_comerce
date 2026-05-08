package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.repository.UsuarioRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Inject
    HashService hashService;

    @Override
    public List<Usuario> findAll() {
        return repository.listAll(Sort.by("id"));
    }

    @Override
    public Usuario findById(Long id) {
        Usuario usuario = repository.findById(id);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        return usuario;
    }

    @Override
    public Usuario findByLogin(String login) {
        Usuario usuario = repository.findByLogin(login);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        return usuario;
    }

    @Override
    public Usuario create(UsuarioRequestDTO dto) {
        validarLoginDisponivel(dto.login(), null);

        Usuario usuario = new Usuario();
        usuario.setLogin(dto.login());
        usuario.setSenhaHash(hashService.hash(dto.senha()));
        usuario.setPerfil(dto.perfil());

        repository.persist(usuario);
        return usuario;
    }

    @Override
    public void update(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = repository.findById(id);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        validarLoginDisponivel(dto.login(), id);

        usuario.setLogin(dto.login());
        usuario.setSenhaHash(hashService.hash(dto.senha()));
        usuario.setPerfil(dto.perfil());
    }

    @Override
    public void delete(Long id) {
        Usuario usuario = repository.findById(id);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        repository.delete(usuario);
    }

    private void validarLoginDisponivel(String login, Long idIgnorado) {
        Usuario usuarioExistente = repository.findByLogin(login);

        if (usuarioExistente != null && (idIgnorado == null || !usuarioExistente.getId().equals(idIgnorado))) {
            throw new WebApplicationException("Login já cadastrado", Response.Status.CONFLICT);
        }
    }
}
