package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.Material;
import br.unitins.tp1.xadrez.e.comerce.repository.MaterialRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class MaterialServiceImpl implements MaterialService {

    @Inject
    MaterialRepository repository;

    @Override
    public List<Material> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Material findById(Long id) {
        Material material = repository.findById(id);

        if (material == null) {
            throw new NotFoundException("Material não encontrado");
        }

        return material;
    }

    @Override
    public List<Material> findByTipo(String tipo) {
        return repository.findByTipo(tipo).list();
    }

    @Override
    @Transactional
    public Material create(Material material) {
        repository.persist(material);
        return material;
    }

    @Override
    @Transactional
    public void update(Long id, Material material) {
        Material existing = repository.findById(id);

        if (existing == null) {
            throw new NotFoundException("Material não encontrado");
        }

        existing.setTipo(material.getTipo());

    }

    @Override
    @Transactional
    public void delete(Long id) {
        Material existing = repository.findById(id);

        if (existing == null) {
            throw new NotFoundException("Material não encontrado");
        }

        repository.delete(existing);
    }
}