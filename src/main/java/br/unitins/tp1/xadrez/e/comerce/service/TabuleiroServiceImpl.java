package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;
import br.unitins.tp1.xadrez.e.comerce.repository.TabuleiroRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TabuleiroServiceImpl implements TabuleiroService {

    @Inject
    TabuleiroRepository repository;

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
    public Tabuleiro create(Tabuleiro tabuleiro) {
        repository.persist(tabuleiro);
        return tabuleiro;
    }

    @Override
    public void update(Long id, Tabuleiro tabuleiro) {
        Tabuleiro entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Tabuleiro não encontrado");
        }

        entidade.setTamanho(tabuleiro.getTamanho());
        entidade.setMaterial(tabuleiro.getMaterial());
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
