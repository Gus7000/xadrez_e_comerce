package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PecaRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Peca;

public interface PecaService {
    List<Peca> findAll();

    Peca findById(Long id);

    List<Peca> findByCor(Long corId);

    List<Peca> findByTipo(Long tipoId);

    List<Peca> findByMaterial(Long materialId);

    Peca create(PecaRequestDTO dto);

    void update(Long id, PecaRequestDTO dto);

    void delete(Long id);
}
