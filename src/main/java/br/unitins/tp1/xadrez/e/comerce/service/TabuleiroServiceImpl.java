package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.TabuleiroRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;
import br.unitins.tp1.xadrez.e.comerce.repository.FabricanteRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.MaterialRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.TabuleiroRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TabuleiroServiceImpl implements TabuleiroService {

    @Inject
    TabuleiroRepository repository;

    @Inject
    MaterialRepository materialRepository;

    @Inject
    FabricanteRepository fabricanteRepository;

    @Override
    public List<Tabuleiro> findAll() {
        return repository.listAll();
    }

    @Override
    public Tabuleiro findById(Long id) {
        Tabuleiro tabuleiro = repository.findById(id);

        if (tabuleiro == null) {
            throw new NotFoundException("Tabuleiro não encontrado");
        }

        return tabuleiro;
    }

    @Override
    public List<Tabuleiro> findByTamanho(String tamanho) {
        return repository.findByTamanho(tamanho).list();
    }

    @Override
    public List<Tabuleiro> findByMaterial(Long materialId) {
        return repository.findByMaterial(materialId).list();
    }

    @Override
    public Tabuleiro create(TabuleiroRequestDTO dto) {
        var material = materialRepository.findById(dto.materialId());
        if (material == null) {
            throw new NotFoundException("Material não encontrado");
        }

        var fabricante = fabricanteRepository.findById(dto.fabricanteId());
        if (fabricante == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }

        Tabuleiro tabuleiro = new Tabuleiro();
        tabuleiro.setTamanho(dto.tamanho());
        tabuleiro.setMaterial(material);
        tabuleiro.setFabricante(fabricante);

        repository.persist(tabuleiro);
        return tabuleiro;
    }

    @Override
    public void update(Long id, TabuleiroRequestDTO dto) {
        Tabuleiro entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Tabuleiro não encontrado");
        }

        var material = materialRepository.findById(dto.materialId());
        if (material == null) {
            throw new NotFoundException("Material não encontrado");
        }

        var fabricante = fabricanteRepository.findById(dto.fabricanteId());
        if (fabricante == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }

        entidade.setTamanho(dto.tamanho());
        entidade.setMaterial(material);
        entidade.setFabricante(fabricante);
    }

    @Override
    public void delete(Long id) {
        Tabuleiro entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Tabuleiro não encontrado");
        }

        repository.delete(entidade);
    }
}
