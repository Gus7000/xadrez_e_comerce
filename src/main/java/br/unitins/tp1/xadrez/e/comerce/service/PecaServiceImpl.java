package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import br.unitins.tp1.xadrez.e.comerce.repository.PecaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PecaServiceImpl implements PecaService {

    @Inject
    PecaRepository repository;

    @Override
    public List<Peca> findAll() {
        return repository.listAll();
    }

    @Override
    public Peca findById(Long id) {
        Peca peca = repository.findById(id);

        if (peca == null) {
            throw new NotFoundException("Peça não encontrada");
        }

        return peca;
    }

    @Override
    public List<Peca> findByCor(Long corId) {
        return repository.findByCor(corId).list();
    }

    @Override
    public List<Peca> findByTipo(Long tipoId) {
        return repository.findByTipo(tipoId).list();
    }

    @Override
    public List<Peca> findByMaterial(Long materialId) {
        return repository.findByMaterial(materialId).list();
    }

    @Override
    public Peca create(Peca peca) {
        repository.persist(peca);
        return peca;
    }

    @Override
    public void update(Long id, Peca peca) {
        Peca entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Peça não encontrada");
        }

        entidade.setCor(peca.getCor());
        entidade.setTipo(peca.getTipo());
        entidade.setMaterial(peca.getMaterial());
    }

    @Override
    public void delete(Long id) {
        Peca entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Peça não encontrada");
        }

        repository.delete(entidade);
    }
}
