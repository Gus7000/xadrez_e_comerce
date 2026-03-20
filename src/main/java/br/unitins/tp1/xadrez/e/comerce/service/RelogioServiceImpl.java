package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.Relogio;
import br.unitins.tp1.xadrez.e.comerce.repository.RelogioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RelogioServiceImpl implements RelogioSrevice {

    @Inject 
    RelogioRepository repository;

    @Override
    public List<Relogio> findAll() {
        return repository.findAll().list();
        
    }

    @Override
    public Relogio findById(Long id) {
       return repository.findById(id);
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
        Relogio relogioCreate = new Relogio();
        repository.persist(relogio);
        return relogioCreate;
    }

    @Override
    @Transactional
    public void update(Long id, Relogio relogio) {
       Relogio r = findById(id);
       r.setModelo(relogio.getModelo());
       r.setTipo(relogio.getTipo());
    }

    @Override
    public void delete(Long id) {
       repository.deleteById(id);
    }
    
}
