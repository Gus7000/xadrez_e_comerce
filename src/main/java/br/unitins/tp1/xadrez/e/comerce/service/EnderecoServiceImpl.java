package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.mapper.EnderecoMapper;
import br.unitins.tp1.xadrez.e.comerce.model.Endereco;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.repository.EnderecoRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.UsuarioRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    @Inject
    EnderecoRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;

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

    @Override
    public Endereco create(Long usuarioId, EnderecoRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(usuarioId);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        Endereco endereco = EnderecoMapper.toEntity(dto, usuario);
        repository.persist(endereco);

        return endereco;
    }

    @Override
    public void update(Long id, Long usuarioId, EnderecoRequestDTO dto) {
        Endereco endereco = repository.findById(id);

        if (endereco == null) {
            throw new NotFoundException("Endereço não encontrado");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId);

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        endereco.setRua(dto.rua());
        endereco.setNumero(dto.numero());
        endereco.setComplemento(dto.complemento());
        endereco.setCep(dto.cep());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setPais(dto.pais());
        endereco.setUsuario(usuario);
    }

    @Override
    public void delete(Long id) {
        Endereco endereco = repository.findById(id);

        if (endereco == null) {
            throw new NotFoundException("Endereço não encontrado");
        }

        repository.delete(endereco);
    }
}
