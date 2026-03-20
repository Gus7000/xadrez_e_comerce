package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.Relogio;

public interface RelogioSrevice {
    List<Relogio> findAll();

    Relogio findById(Long id);

    List<Relogio> findByMarca(String marca);

    List <Relogio> findByTipo(Long idTipo);

    Relogio create(Relogio relogio);

    void update(Long id, Relogio relogio);

    void delete(Long id);
}
