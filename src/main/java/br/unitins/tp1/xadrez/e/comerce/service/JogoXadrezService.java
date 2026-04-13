package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;

public interface JogoXadrezService {
    List<JogoXadrez> findAll();

    JogoXadrez findById(Long id);

    List<JogoXadrez> findByKitPeca(Long kitPecaId);

    List<JogoXadrez> findByTabuleiro(Long tabuleiroId);

    JogoXadrez create(JogoXadrez jogoXadrez);

    void update(Long id, JogoXadrez jogoXadrez);

    void delete(Long id);
}
