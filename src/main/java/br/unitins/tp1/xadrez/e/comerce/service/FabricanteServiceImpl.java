package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.repository.FabricanteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class FabricanteServiceImpl implements FabricanteService {

    @Inject
    FabricanteRepository repository;

    @Override
    public List<Fabricante> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Fabricante findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Fabricante> findByNome(String nome) {
        return repository.findByNome(nome).list();
    }

    @Override
    public Fabricante findByCnpj(String cnpj) {
        return repository.findByCnpj(cnpj);
    }

    @Override
    @Transactional
    public Fabricante create(Fabricante fabricante) {
        repository.persist(fabricante);
        return fabricante;
    }

    @Override
    @Transactional
    public void update(Long id, Fabricante fabricante) {
        Fabricante existing = findById(id);
        if (existing == null)
            return;
        existing.setNome(fabricante.getNome());
        existing.setCnpj(fabricante.getCnpj());
        existing.setTelefone(fabricante.getTelefone());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
