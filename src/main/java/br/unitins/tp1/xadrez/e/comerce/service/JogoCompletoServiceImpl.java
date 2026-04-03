package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.JogoCompleto;
import br.unitins.tp1.xadrez.e.comerce.repository.JogoCompletoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class JogoCompletoServiceImpl implements JogoCompletoService {

    @Inject
    JogoCompletoRepository repository;

    @Override
    public List<JogoCompleto> findAll() {
        return repository.listAll();
    }

    @Override
    public JogoCompleto findById(Long id) {
        JogoCompleto jogoCompleto = repository.findById(id);

        if (jogoCompleto == null) {
            throw new NotFoundException("Jogo Completo não encontrado");
        }

        return jogoCompleto;
    }

    @Override
    public List<JogoCompleto> findByKitPeca(Long kitPecaId) {
        return repository.findByKitPeca(kitPecaId).list();
    }

    @Override
    public List<JogoCompleto> findByTabuleiro(Long tabuleiroId) {
        return repository.findByTabuleiro(tabuleiroId).list();
    }

    @Override
    public JogoCompleto create(JogoCompleto jogoCompleto) {
        repository.persist(jogoCompleto);
        return jogoCompleto;
    }

    @Override
    public void update(Long id, JogoCompleto jogoCompleto) {
        JogoCompleto entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Jogo Completo não encontrado");
        }

        entidade.setKitPeca(jogoCompleto.getKitPeca());
        entidade.setTabuleiro(jogoCompleto.getTabuleiro());
    }

    @Override
    public void delete(Long id) {
        JogoCompleto entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Jogo Completo não encontrado");
        }

        repository.delete(entidade);
    }
}
