package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;

public interface TabuleiroService {
    List<Tabuleiro> findAll();

    Tabuleiro findById(Long id);

    List<Tabuleiro> findByTamanho(String tamanho);

    List<Tabuleiro> findByMaterial(Long materialId);

    Tabuleiro create(TabuleiroRequestDTO dto);

    void update(Long id, TabuleiroRequestDTO dto);

    void delete(Long id);
}
