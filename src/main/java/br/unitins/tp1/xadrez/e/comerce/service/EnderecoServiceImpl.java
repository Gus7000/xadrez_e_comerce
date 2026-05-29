package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.EnderecoMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Endereco;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.repository.EnderecoRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository repository;

    @Inject
    UsuarioService usuarioService;

    // --- Consultas Gerais (Admin) ---

    @Override
    public List<Endereco> findAll() {
        return repository.listAll(Sort.by("id"));
    }

    @Override
    public Endereco findById(Long id) {
        Endereco endereco = repository.findById(id);
        if (endereco == null) {
            throw new NotFoundException("Endereço não encontrado");
        }
        return endereco;
    }

    @Override
    public List<Endereco> findByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId).list();
    }

    // --- Operações do Usuário Logado ---

    @Override
    public List<Endereco> findMyEnderecos() { // <-- Alterado para o padrão em inglês
        Usuario usuario = usuarioService.obterOuCriarUsuarioLocal();
        return repository.findByUsuarioId(usuario.getId()).list();
    }

    @Override
    @Transactional
    public Endereco create(EnderecoRequestDTO dto) {
        Usuario usuario = usuarioService.obterOuCriarUsuarioLocal();
        Endereco endereco = EnderecoMapper.toEntity(dto, usuario);
        repository.persist(endereco);
        return endereco;
    }

    @Override
    @Transactional
    public void update(Long id, EnderecoRequestDTO dto) {
        Usuario usuario = usuarioService.obterOuCriarUsuarioLocal();
        Endereco endereco = repository.findById(id);

        if (endereco == null || !endereco.getUsuario().getId().equals(usuario.getId())) {
            throw new NotFoundException("Endereço não encontrado");
        }

        endereco.setRua(dto.rua());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setCep(dto.cep());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setPais(dto.pais());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Usuario usuario = usuarioService.obterOuCriarUsuarioLocal();
        Endereco endereco = repository.findById(id);

        if (endereco == null || !endereco.getUsuario().getId().equals(usuario.getId())) {
            throw new NotFoundException("Endereço não encontrado");
        }

        repository.delete(endereco);
    }
}