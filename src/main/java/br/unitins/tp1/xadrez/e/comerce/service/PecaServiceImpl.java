package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PecaRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.CorPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import br.unitins.tp1.xadrez.e.comerce.model.TipoPeca;
import br.unitins.tp1.xadrez.e.comerce.repository.MaterialRepository;
import br.unitins.tp1.xadrez.e.comerce.repository.PecaRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class PecaServiceImpl implements PecaService {

    @Inject
    PecaRepository repository;

    @Inject
    MaterialRepository materialRepository;

    @Override
    public List<Peca> findAll() {
        return repository.listAll(Sort.by("id"));
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
        CorPeca cor = CorPeca.valueOf(corId);
    return repository.findByCor(cor).list();
    }

    @Override
    public List<Peca> findByTipo(Long tipoId) {
        TipoPeca tipo = TipoPeca.valueOf(tipoId);
    return repository.findByTipo(tipo).list();
    }

    @Override
    public List<Peca> findByMaterial(Long materialId) {
        return repository.findByMaterial(materialId).list();
    }

    @Override
    public Peca create(PecaRequestDTO dto) {
        var material = materialRepository.findById(dto.materialId());
        if (material == null) {
            throw new NotFoundException("Material não encontrado");
        }

        Peca peca = new Peca();
        peca.setCor(CorPeca.valueOf(dto.corId()));
        peca.setTipo(TipoPeca.valueOf(dto.tipoId()));
        peca.setMaterial(material);
        peca.setDiametroCm(dto.diametroCm());
        peca.setAlturaCm(dto.alturaCm());

        repository.persist(peca);
        return peca;
    }

    @Override
    public void update(Long id, PecaRequestDTO dto) {
        Peca entidade = repository.findById(id);

        if (entidade == null) {
            throw new NotFoundException("Peça não encontrada");
        }

        var material = materialRepository.findById(dto.materialId());
        if (material == null) {
            throw new NotFoundException("Material não encontrado");
        }

        entidade.setCor(CorPeca.valueOf(dto.corId()));
        entidade.setTipo(TipoPeca.valueOf(dto.tipoId()));
        entidade.setMaterial(material);
        entidade.setDiametroCm(dto.diametroCm());
        entidade.setAlturaCm(dto.alturaCm());
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
