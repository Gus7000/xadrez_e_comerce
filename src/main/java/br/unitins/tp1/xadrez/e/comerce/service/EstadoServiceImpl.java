package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;


import br.unitins.tp1.xadrez.e.comerce.model.Estado;
import br.unitins.tp1.xadrez.e.comerce.repository.EstadoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {

    @Inject
    EstadoRepository repository;

    @Override
    public List<Estado> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Estado findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Estado findBySigla(String sigla) {
        return (Estado) repository.findBySigla(sigla);
    }

    @Override
    public List<Estado> findByNome(String nome) {
        return repository.findByNome(nome).list();

    }

    @Override
    @Transactional
    public Estado create(Estado estado) {
        Estado estadoCreate = new Estado();
        repository.persist(estado);
        return estadoCreate;
    }

    @Override
    @Transactional
    public void update(Long id, Estado obj) {
        Estado e = findById(id);
       e.setNome(obj.getNome());
       e.setSigla(obj.getSigla());
       e.setRegiao(obj.getRegiao());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

}
