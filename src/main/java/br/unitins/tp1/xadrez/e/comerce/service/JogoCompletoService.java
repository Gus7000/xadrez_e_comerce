package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.JogoCompleto;

public interface JogoCompletoService {
    List<JogoCompleto> findAll();

    JogoCompleto findById(Long id);

    List<JogoCompleto> findByKitPeca(Long kitPecaId);

    List<JogoCompleto> findByTabuleiro(Long tabuleiroId);

    JogoCompleto create(JogoCompleto jogoCompleto);

    void update(Long id, JogoCompleto jogoCompleto);

    void delete(Long id);
}
