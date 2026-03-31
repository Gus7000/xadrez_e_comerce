package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;
import br.unitins.tp1.xadrez.e.comerce.repository.FabricanteRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class FabricanteServiceImpl {

    @Inject
    FabricanteRepository repository;

    public List<Fabricante> findAll() {
        return repository.listAll();
    }

    public Fabricante findById(Long id) {
        Fabricante fabricante = repository.findById(id);

        if (fabricante == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }

        return fabricante;
    }

    public List<Fabricante> findByNome(String nome) {
        return repository.findByNome(nome).list();
    }

    public Fabricante findByCnpj(String cnpj) {
        Fabricante fabricante = repository.findByCnpj(cnpj);

        if (fabricante == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }

        return fabricante;
    }

    public Fabricante create(Fabricante fabricante) {
        repository.persist(fabricante);
        return fabricante;
    }

    public void update(Long id, Fabricante fabricante) {
        Fabricante entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }

        entidade.setNome(fabricante.getNome());
        entidade.setCnpj(fabricante.getCnpj());
        entidade.setTelefone(fabricante.getTelefone());
    }

    public void delete(Long id) {
        Fabricante entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }

        repository.delete(entidade);
    }
}