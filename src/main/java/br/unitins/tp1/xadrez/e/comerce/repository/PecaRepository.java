package br.unitins.tp1.xadrez.e.comerce.repository;

import br.unitins.tp1.xadrez.e.comerce.model.CorPeca;
import br.unitins.tp1.xadrez.e.comerce.model.Peca;
import br.unitins.tp1.xadrez.e.comerce.model.TipoPeca;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PecaRepository implements PanacheRepository<Peca> {

    public PanacheQuery<Peca> findByCor(CorPeca cor) {
        return find("cor", cor);
    }

    public PanacheQuery<Peca> findByTipo(TipoPeca tipo) {
        return find("tipo", tipo);
    }

    public PanacheQuery<Peca> findByMaterial(Long materialId) {
        return find("material.id", materialId);
    }
}
