package br.unitins.tp1.xadrez.e.comerce.repository;

import br.unitins.tp1.xadrez.e.comerce.model.JogoCompleto;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JogoCompletoRepository implements PanacheRepository<JogoCompleto> {

    public PanacheQuery<JogoCompleto> findByKitPeca(Long kitPecaId) {
        return find("kitPeca.id", kitPecaId);
    }

    public PanacheQuery<JogoCompleto> findByTabuleiro(Long tabuleiroId) {
        return find("tabuleiro.id", tabuleiroId);
    }
}
