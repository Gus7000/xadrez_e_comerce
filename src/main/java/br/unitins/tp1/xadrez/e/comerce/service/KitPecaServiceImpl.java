package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;
import br.unitins.tp1.xadrez.e.comerce.repository.KitPecaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class KitPecaServiceImpl implements KitPecaService {

    @Inject
    KitPecaRepository repository;

    @Override
    public List<KitPeca> findAll() {
        return repository.listAll();
    }

    @Override
    public KitPeca findById(Long id) {
        KitPeca kitPeca = repository.findById(id);

        if (kitPeca == null) {
            throw new NotFoundException("Kit de Peças não encontrado");
        }

        return kitPeca;
    }

    @Override
    public KitPeca create(KitPeca kitPeca) {
        repository.persist(kitPeca);
        return kitPeca;
    }

    @Override
    public void update(Long id, KitPeca kitPeca) {
        KitPeca entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Kit de Peças não encontrado");
        }

        entidade.setItens(kitPeca.getItens());
    }

    @Override
    public void delete(Long id) {
        KitPeca entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Kit de Peças não encontrado");
        }

        repository.delete(entidade);
    }
}
