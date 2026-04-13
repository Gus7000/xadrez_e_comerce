package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import br.unitins.tp1.xadrez.e.comerce.repository.JogoXadrezRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class JogoXadrezServiceImpl implements JogoXadrezService {

    @Inject
    JogoXadrezRepository repository;

    @Override
    public List<JogoXadrez> findAll() {
        return repository.listAll();
    }

    @Override
    public JogoXadrez findById(Long id) {
        JogoXadrez jogoXadrez = repository.findById(id);

        if (jogoXadrez == null) {
            throw new NotFoundException("Jogo Completo não encontrado");
        }

        return jogoXadrez;
    }

    @Override
    public List<JogoXadrez> findByKitPeca(Long kitPecaId) {
        return repository.findByKitPeca(kitPecaId).list();
    }

    @Override
    public List<JogoXadrez> findByTabuleiro(Long tabuleiroId) {
        return repository.findByTabuleiro(tabuleiroId).list();
    }

    @Override
    public JogoXadrez create(JogoXadrez jogoXadrez) {
        repository.persist(jogoXadrez);
        return jogoXadrez;
    }

    @Override
    public void update(Long id, JogoXadrez jogoXadrez) {
        JogoXadrez entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Jogo Completo não encontrado");
        }

        entidade.setNome(jogoXadrez.getNome());
        entidade.setPreco(jogoXadrez.getPreco());
        entidade.setDescricao(jogoXadrez.getDescricao());
        entidade.setKitPeca(jogoXadrez.getKitPeca());
        entidade.setTabuleiro(jogoXadrez.getTabuleiro());
    }

    @Override
    public void delete(Long id) {
        JogoXadrez entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Jogo Completo não encontrado");
        }

        repository.delete(entidade);
    }
}
