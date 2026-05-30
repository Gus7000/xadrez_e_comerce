package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.xadrez.e.comerce.DTO.MeResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioPerfilUpdateDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.EnderecoMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.repository.EnderecoRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.UsuarioRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.keycloak.representations.idm.UserRepresentation;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Inject
    JsonWebToken jwt;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    KeycloakAdminService keycloakAdminService;

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
    public Usuario findByEmail(String email) {
        Usuario usuario = repository.findByEmail(email);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        return usuario;
    }

    @Override
    public Usuario findByKeycloakId(String keycloakId) {
        Usuario usuario = repository.findByKeycloakId(keycloakId);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        return usuario;
    }

    @Override
    @Transactional
    public Usuario localizarOuCriarPorKeycloak(UserRepresentation keycloakUser) {
        if (keycloakUser == null) {
            return null;
        }

        String keycloakId = keycloakUser.getId();
        String email = keycloakUser.getEmail();
        String nome = keycloakUser.getFirstName();

        Usuario usuario = null;
        if (keycloakId != null && !keycloakId.isBlank()) {
            usuario = repository.findByKeycloakId(keycloakId);
        }

        if (usuario == null && email != null && !email.isBlank()) {
            usuario = repository.findByEmail(email);
        }

        if (usuario == null) {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setKeycloakId(keycloakId);
            novoUsuario.setEmail(email);
            novoUsuario.setNome(nome);
            novoUsuario.setCadastroCompleto(false);
            repository.persist(novoUsuario);
            repository.flush();
            return novoUsuario;
        }

        if ((usuario.getKeycloakId() == null || usuario.getKeycloakId().isBlank()) && keycloakId != null && !keycloakId.isBlank()) {
            usuario.setKeycloakId(keycloakId);
        }

        if ((usuario.getEmail() == null || usuario.getEmail().isBlank()) && email != null && !email.isBlank()) {
            usuario.setEmail(email);
        }

        if ((usuario.getNome() == null || usuario.getNome().isBlank()) && nome != null && !nome.isBlank()) {
            usuario.setNome(nome);
        }

        return usuario;
    }

    @Override
    @Transactional
    public Usuario obterOuCriarUsuarioLocal() {
        String keycloakId = jwt.getSubject();
        Usuario usuario = repository.findByKeycloakId(keycloakId);

        if (usuario != null) {
            // recalcula cadastroCompleto: se já não estiver marcado e
            // o usuário tiver pelo menos um endereço e os dados pessoais preenchidos,
            // marca como completo
            if (!usuario.isCadastroCompleto()) {
                boolean temNome = usuario.getNome() != null && !usuario.getNome().isBlank();
                boolean temTelefone = usuario.getTelefone() != null && !usuario.getTelefone().isBlank();
                boolean temCpf = usuario.getCpf() != null && !usuario.getCpf().isBlank();
                boolean temEndereco = enderecoRepository.findByUsuarioId(usuario.getId()).firstResult() != null;

                if (temEndereco && temNome && temTelefone && temCpf) {
                    usuario.setCadastroCompleto(true);
                    repository.flush();
                }
            }

            return usuario;
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setKeycloakId(keycloakId);
        novoUsuario.setEmail((String) jwt.getClaim("email"));
        novoUsuario.setNome((String) jwt.getClaim("preferred_username"));
        novoUsuario.setCadastroCompleto(false);

        repository.persist(novoUsuario);
        repository.flush();

        return novoUsuario;
    }

    @Override
    @Transactional
    public MeResponseDTO obterMeuPerfil() {
        Usuario usuario = obterOuCriarUsuarioLocal();

        List<br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoResponseDTO> enderecos = usuario.getEnderecos()
                .stream()
                .map(EnderecoMapper::toResponseDTO)
                .collect(Collectors.toList());

        return new MeResponseDTO(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getKeycloakId(),
                usuario.getNome(),
                usuario.getTelefone(),
                usuario.getCpf(),
                usuario.isCadastroCompleto(),
                enderecos,
                usuario.getDataCadastro());
    }

    @Override
    @Transactional
    public Usuario atualizarPerfil(UsuarioPerfilUpdateDTO dto) {
        Usuario usuario = obterOuCriarUsuarioLocal();

        usuario.setNome(dto.nome());
        usuario.setCpf(dto.cpf());
        usuario.setTelefone(dto.telefone());

        boolean temNome = usuario.getNome() != null && !usuario.getNome().isBlank();
        boolean temTelefone = usuario.getTelefone() != null && !usuario.getTelefone().isBlank();
        boolean temCpf = usuario.getCpf() != null && !usuario.getCpf().isBlank();
        boolean temEndereco = enderecoRepository.findByUsuarioId(usuario.getId()).firstResult() != null;

        usuario.setCadastroCompleto(temNome && temTelefone && temCpf && temEndereco);

        return usuario;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        delete(String.valueOf(id));
    }

    @Override
    @Transactional
    public void delete(String identificador) {
        Usuario usuario = localizarUsuarioParaAdmin(identificador);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        if (usuario.getKeycloakId() != null) {
            keycloakAdminService.deletarUsuario(usuario.getKeycloakId());
        }

        repository.delete(usuario);
    }

    @Override
    @Transactional
    public void promoteToAdmin(Long id) {
        promoteToAdmin(String.valueOf(id));
    }

    @Override
    @Transactional
    public void promoteToAdmin(String identificador) {
        Usuario usuario = localizarUsuarioParaAdmin(identificador);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        try {
            String kcId = usuario.getKeycloakId();
            if (kcId == null) {
                kcId = keycloakAdminService.createUser(usuario.getEmail());
                usuario.setKeycloakId(kcId);
            }
            keycloakAdminService.promoverParaAdmin(kcId);
        } catch (Exception e) {
            throw new WebApplicationException("Erro ao promover usuário no Keycloak", 500);
        }
    }

    @Override
    @Transactional
    public void demoteFromAdmin(Long id) {
        demoteFromAdmin(String.valueOf(id));
    }

    @Override
    @Transactional
    public void demoteFromAdmin(String identificador) {
        Usuario usuario = localizarUsuarioParaAdmin(identificador);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        String kcId = usuario.getKeycloakId();
        if (kcId == null) {
            return;
        }

        try {
            int admins = keycloakAdminService.countUsersWithRealmRole("ADMIN");
            if (admins <= 1) {
                throw new WebApplicationException("Não é possível remover o último administrador", Response.Status.BAD_REQUEST);
            }
            keycloakAdminService.removerRoleAdmin(kcId);
        } catch (Exception e) {
            throw new WebApplicationException("Erro ao remover role do usuário no Keycloak", 500);
        }
    }

    private Usuario localizarUsuarioParaAdmin(String identificador) {
        if (identificador == null || identificador.isBlank()) {
            return null;
        }

        try {
            Long id = Long.valueOf(identificador);
            Usuario usuarioPorId = repository.findById(id);
            if (usuarioPorId != null) {
                return usuarioPorId;
            }
        } catch (NumberFormatException ignored) {
            // not a local numeric id, keep trying other identifiers
        }

        Usuario usuario = repository.findByKeycloakId(identificador);
        if (usuario != null) {
            return usuario;
        }

        usuario = repository.findByEmail(identificador);
        if (usuario != null) {
            return usuario;
        }

        UserRepresentation keycloakUser = keycloakAdminService.obterUsuario(identificador);
        if (keycloakUser != null) {
            return localizarOuCriarPorKeycloak(keycloakUser);
        }

        return null;
    }
}
