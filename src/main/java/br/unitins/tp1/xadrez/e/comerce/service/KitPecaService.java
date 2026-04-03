package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;

public interface KitPecaService {
    List<KitPeca> findAll();

    KitPeca findById(Long id);

    KitPeca create(KitPeca kitPeca);

    void update(Long id, KitPeca kitPeca);

    void delete(Long id);
}
