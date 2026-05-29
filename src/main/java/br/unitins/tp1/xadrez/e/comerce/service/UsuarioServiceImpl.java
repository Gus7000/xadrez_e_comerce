package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.xadrez.e.comerce.DTO.MeResponseDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioPerfilUpdateDTO;
import br.unitins.tp1.xadrez.e.comerce.DTO.UsuarioRequestDTO;
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

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository repository;

    @Inject
    JsonWebToken jwt;

    @Inject
    EnderecoRepository enderecoRepository;

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
    public Usuario create(UsuarioRequestDTO dto) {
        validarEmailDisponivel(dto.email(), null);

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.email());
        usuario.setKeycloakId(dto.keycloakId());

        repository.persist(usuario);
        return usuario;
    }

    @Override
    public void update(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = repository.findById(id);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        validarEmailDisponivel(dto.email(), id);

        usuario.setEmail(dto.email());
        usuario.setKeycloakId(dto.keycloakId());
    }

    @Override
    public void delete(Long id) {
        Usuario usuario = repository.findById(id);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        repository.delete(usuario);
    }

    private void validarEmailDisponivel(String email, Long idIgnorado) {
        Usuario usuarioExistente = repository.findByEmail(email);

        if (usuarioExistente != null && (idIgnorado == null || !usuarioExistente.getId().equals(idIgnorado))) {
            throw new WebApplicationException("Email já cadastrado", Response.Status.CONFLICT);
        }
    }
}
