package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;

public interface TabuleiroService {
    List<Tabuleiro> findAll();

    Tabuleiro findById(Long id);

    List<Tabuleiro> findByTamanho(String tamanho);

    List<Tabuleiro> findByCor(Long corId);

    List<Tabuleiro> findByMaterial(Long materialId);

    Tabuleiro create(Tabuleiro tabuleiro);

    void update(Long id, Tabuleiro tabuleiro);

    void delete(Long id);
}
