package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.repository.FabricanteRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class FabricanteServiceImpl implements FabricanteService {

    @Inject
    FabricanteRepository repository;

    @Override
    public List<Fabricante> findAll() {
        return repository.listAll(Sort.by("id"));
    }

    @Override
    public Fabricante findById(Long id) {
        Fabricante fabricante = repository.findById(id);

        if (fabricante == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }

        return fabricante;
    }

    @Override
    public List<Fabricante> findByNome(String nome) {
        return repository.findByNome(nome).list();
    }

    @Override
    public Fabricante findByCnpj(String cnpj) {
        Fabricante fabricante = repository.findByCnpj(cnpj);

        if (fabricante == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }

        return fabricante;
    }

    @Override
    public Fabricante create(Fabricante fabricante) {
        repository.persist(fabricante);
        return fabricante;
    }

    @Override
    public void update(Long id, Fabricante fabricante) {
        Fabricante entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }

        entidade.setNome(fabricante.getNome());
        entidade.setCnpj(fabricante.getCnpj());
        entidade.setTelefone(fabricante.getTelefone());
    }

    @Override
    public void delete(Long id) {
        Fabricante entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }

        repository.delete(entidade);
    }
}