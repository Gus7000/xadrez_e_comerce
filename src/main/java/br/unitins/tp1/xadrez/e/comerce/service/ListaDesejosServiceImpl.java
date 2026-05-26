package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.unitins.tp1.xadrez.e.comerce.DTO.ListaDesejosRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.model.ListaDesejos;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.repository.JogoXadrezRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.ListaDesejosRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.UsuarioRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ListaDesejosServiceImpl implements ListaDesejosService {

    @Inject
    ListaDesejosRepository repository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    JogoXadrezRepository jogoXadrezRepository;

    @Override
    public List<ListaDesejos> findAll() {
        return repository.listAll(Sort.by("id"));
    }

    @Override
    public ListaDesejos findById(Long id) {
        ListaDesejos listaDesejos = repository.findById(id);

        if (listaDesejos == null) {
            throw new NotFoundException("Lista de desejos não encontrada");
        }

        return listaDesejos;
    }

    @Override
    public List<ListaDesejos> findByUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId).list();
    }

    @Override
    public ListaDesejos create(ListaDesejosRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.usuarioId());

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        ListaDesejos listaDesejos = new ListaDesejos();
        listaDesejos.setUsuario(usuario);
        listaDesejos.setJogos(buscarJogos(dto.jogoIds()));

        repository.persist(listaDesejos);
        return listaDesejos;
    }

    @Override
    public void update(Long id, ListaDesejosRequestDTO dto) {
        ListaDesejos listaDesejos = repository.findById(id);

        if (listaDesejos == null) {
            throw new NotFoundException("Lista de desejos não encontrada");
        }

        Usuario usuario = usuarioRepository.findById(dto.usuarioId());

        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        listaDesejos.setUsuario(usuario);
        listaDesejos.setJogos(buscarJogos(dto.jogoIds()));
    }

    @Override
    public void delete(Long id) {
        ListaDesejos listaDesejos = repository.findById(id);

        if (listaDesejos == null) {
            throw new NotFoundException("Lista de desejos não encontrada");
        }

        repository.delete(listaDesejos);
    }

    @Override
    public ListaDesejos findOrCreateByUsuarioId(Long usuarioId) {
        ListaDesejos lista = repository.findByUsuarioId(usuarioId).firstResult();
        if (lista != null) {
            return lista;
        }

        Usuario usuario = usuarioRepository.findById(usuarioId);
        if (usuario == null) {
            throw new NotFoundException("Usuário não encontrado");
        }

        ListaDesejos nova = new ListaDesejos();
        nova.setUsuario(usuario);
        nova.setJogos(new HashSet<>());
        repository.persist(nova);
        return nova;
    }

    @Override
    public void addItem(Long usuarioId, Long jogoId) {
        ListaDesejos lista = findOrCreateByUsuarioId(usuarioId);
        JogoXadrez jogo = jogoXadrezRepository.findById(jogoId);
        if (jogo == null) {
            throw new NotFoundException("Jogo não encontrado: " + jogoId);
        }
        lista.getJogos().add(jogo);
    }

    @Override
    public void removeItem(Long usuarioId, Long jogoId) {
        ListaDesejos lista = repository.findByUsuarioId(usuarioId).firstResult();
        if (lista == null) {
            throw new NotFoundException("Lista de desejos não encontrada para o usuário");
        }
        JogoXadrez jogo = jogoXadrezRepository.findById(jogoId);
        if (jogo == null) {
            throw new NotFoundException("Jogo não encontrado: " + jogoId);
        }
        lista.getJogos().remove(jogo);
    }

    private Set<JogoXadrez> buscarJogos(List<Long> jogoIds) {
        Set<JogoXadrez> jogos = new HashSet<>();

        for (Long jogoId : jogoIds) {
            JogoXadrez jogo = jogoXadrezRepository.findById(jogoId);
            if (jogo == null) {
                throw new NotFoundException("Jogo não encontrado: " + jogoId);
            }
            jogos.add(jogo);
        }

        return jogos;
    }
}
