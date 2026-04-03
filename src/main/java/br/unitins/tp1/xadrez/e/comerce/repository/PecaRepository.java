package br.unitins.tp1.xadrez.e.comerce.repository;

import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PecaRepository implements PanacheRepository<Peca> {

    public PanacheQuery<Peca> findByCor(Long corId) {
        return find("cor", corId);
    }

    public PanacheQuery<Peca> findByTipo(Long tipoId) {
        return find("tipo", tipoId);
    }

    public PanacheQuery<Peca> findByMaterial(Long materialId) {
        return find("material.id", materialId);
    }
}
