package br.unitins.tp1.xadrez.e.comerce.repository;

import br.unitins.tp1.xadrez.e.comerce.model.Tabuleiro;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TabuleiroRepository implements PanacheRepository<Tabuleiro> {

    public PanacheQuery<Tabuleiro> findByTamanho(String tamanho) {
        return find("UPPER(tamanho) LIKE UPPER(?1)", "%" + tamanho + "%");
    }

    public PanacheQuery<Tabuleiro> findByMaterial(Long materialId) {
        return find("material.id", materialId);
    }
}
