package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.HashSet;
import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.model.ListaDesejos;
import br.unitins.tp1.xadrez.e.comerce.model.Usuario;
import br.unitins.tp1.xadrez.e.comerce.repository.JogoXadrezRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.ListaDesejosRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ListaDesejosServiceImpl implements ListaDesejosService {

    @Inject
    ListaDesejosRepository repository;

    @Inject
    JogoXadrezRepository jogoXadrezRepository;

    @Inject
    UsuarioService usuarioService;

    // --- Consultas Gerais (Admin) ---

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

    // --- Operações do Usuário Logado (/me) ---

    @Override
    @Transactional
    public ListaDesejos findMyLista() {
        Usuario usuario = usuarioService.obterOuCriarUsuarioLocal();
        ListaDesejos lista = repository.findByUsuarioId(usuario.getId()).firstResult();

        if (lista != null) {
            return lista;
        }

        // Cria a lista dinamicamente se o usuário ainda não tiver uma
        ListaDesejos nova = new ListaDesejos();
        nova.setUsuario(usuario);
        usuario.setListaDesejos(nova);
        nova.setJogos(new HashSet<>());
        repository.persist(nova);
        
        return nova;
    }

    @Override
    @Transactional
    public void addItem(Long jogoId) {
        ListaDesejos lista = findMyLista();
        JogoXadrez jogo = jogoXadrezRepository.findById(jogoId);
        
        if (jogo == null) {
            throw new NotFoundException("Jogo não encontrado: " + jogoId);
        }
        
        lista.getJogos().add(jogo);
    }

    @Override
    @Transactional
    public void removeItem(Long jogoId) {
        Usuario usuario = usuarioService.obterOuCriarUsuarioLocal();
        ListaDesejos lista = repository.findByUsuarioId(usuario.getId()).firstResult();
        
        if (lista == null) {
            throw new NotFoundException("Lista de desejos não encontrada");
        }

        JogoXadrez jogo = jogoXadrezRepository.findById(jogoId);
        
        if (jogo == null) {
            throw new NotFoundException("Jogo não encontrado: " + jogoId);
        }
        
        lista.getJogos().remove(jogo);
    }
}