package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;
import java.util.stream.Collectors;

import br.unitins.tp1.xadrez.e.comerce.DTO.RelogioRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Relogio;
import br.unitins.tp1.xadrez.e.comerce.model.RelogioAnalogico;
import br.unitins.tp1.xadrez.e.comerce.model.RelogioDigital;
import br.unitins.tp1.xadrez.e.comerce.repository.FabricanteRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.RelogioRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class RelogioServiceImpl implements RelogioService {

    @Inject 
    RelogioRepository repository;

    @Inject
    FabricanteRepository fabricanteRepository;

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
        if (idTipo == null) {
            throw new IllegalArgumentException("ID do tipo não pode ser nulo");
        }
        
        List<Relogio> todos = repository.findAll().list();
        
        if (idTipo == 1) {
            return todos.stream()
                    .filter(r -> r instanceof RelogioDigital)
                    .collect(Collectors.toList());
        } else if (idTipo == 2) {
            return todos.stream()
                    .filter(r -> r instanceof RelogioAnalogico)
                    .collect(Collectors.toList());
        }
        
        throw new IllegalArgumentException("Tipo de relógio inválido. Use 1 para Digital ou 2 para Analógico");
    }

    public List<Relogio> findByTipo(String tipo) {
        List<Relogio> todos = repository.findAll().list();
        
        if ("digital".equalsIgnoreCase(tipo)) {
            return todos.stream()
                    .filter(r -> r instanceof RelogioDigital)
                    .collect(Collectors.toList());
        } else if ("analogico".equalsIgnoreCase(tipo)) {
            return todos.stream()
                    .filter(r -> r instanceof RelogioAnalogico)
                    .collect(Collectors.toList());
        }
        return todos;
    }

    @Override
    @Transactional
    public Relogio create(RelogioRequestDTO dto) {
        var fabricante = fabricanteRepository.findById(dto.fabricanteId());
        if (fabricante == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }

        RelogioDigital relogio = new RelogioDigital();
        relogio.setModelo(dto.modelo());
        relogio.setDimensoes(dto.dimensoes());
        relogio.setFabricante(fabricante);

        repository.persist(relogio);
        return relogio; 
    }

    @Override
    @Transactional
    public void update(Long id, RelogioRequestDTO dto) {
        Relogio existente = repository.findById(id);

        if (existente == null) {
            throw new NotFoundException("Relógio não encontrado");
        }

        var fabricante = fabricanteRepository.findById(dto.fabricanteId());
        if (fabricante == null) {
            throw new NotFoundException("Fabricante não encontrado");
        }

        existente.setModelo(dto.modelo());
        existente.setDimensoes(dto.dimensoes());
        existente.setFabricante(fabricante);
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