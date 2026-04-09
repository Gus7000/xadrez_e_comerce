package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.Relogio;
import br.unitins.tp1.xadrez.e.comerce.repository.RelogioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class RelogioServiceImpl implements RelogioService {

    @Inject 
    RelogioRepository repository;

    @Override
    public List<Relogio> findAll() {
        return repository.findAll().list();
    }

    @Override
    public Relogio findById(Long id) {
        Relogio relogio = repository.findById(id);

        if (relogio == null) {
            throw new NotFoundException("Relógio não encontrado");
        }

        return relogio;
    }

    @Override
    public List<Relogio> findByMarca(String marca) {
        return repository.findByMarca(marca).list();
    }

    @Override
    public List<Relogio> findByTipo(Long idTipo) {
        return repository.findByTipoRelogio(idTipo).list();
    }

    @Override
    @Transactional
    public Relogio create(Relogio relogio) {
        repository.persist(relogio);
        return relogio; 
    }

    @Override
    @Transactional
    public void update(Long id, Relogio relogio) {
        Relogio existente = repository.findById(id);

        if (existente == null) {
            throw new NotFoundException("Relógio não encontrado");
        }


        existente.setModelo(relogio.getModelo());
        existente.setTipo(relogio.getTipo());
        existente.setFabricante(relogio.getFabricante());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Relogio existente = repository.findById(id);

        if (existente == null) {
            throw new NotFoundException("Relógio não encontrado");
        }

        repository.delete(existente);
    }
}