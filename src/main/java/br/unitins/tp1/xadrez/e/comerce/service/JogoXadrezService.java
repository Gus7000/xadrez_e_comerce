package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.JogoXadrezRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;

public interface JogoXadrezService {
    List<JogoXadrez> findAll();

    JogoXadrez findById(Long id);

    List<JogoXadrez> findByKitPeca(Long kitPecaId);

    List<JogoXadrez> findByTabuleiro(Long tabuleiroId);

    JogoXadrez create(JogoXadrezRequestDTO dto);

    void update(Long id, JogoXadrezRequestDTO dto);

    void delete(Long id);
}
