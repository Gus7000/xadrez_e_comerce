package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.Material;

public interface MaterialService {
    List<Material> findAll();

    Material findById(Long id);

    List<Material> findByTipo(String tipo);

    Material create(Material material);

    void update(Long id, Material material);

    void delete(Long id);
}
