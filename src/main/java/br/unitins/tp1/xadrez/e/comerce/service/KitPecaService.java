package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.KitPecaRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;

public interface KitPecaService {
    List<KitPeca> findAll();

    KitPeca findById(Long id);

    KitPeca create(KitPecaRequestDTO dto);

    void update(Long id, KitPecaRequestDTO dto);

    void delete(Long id);
}
