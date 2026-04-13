package br.unitins.tp1.xadrez.e.comerce.repository;

import br.unitins.tp1.xadrez.e.comerce.model.JogoXadrez;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class JogoXadrezRepository implements PanacheRepository<JogoXadrez> {

    public PanacheQuery<JogoXadrez> findByKitPeca(Long kitPecaId) {
        return find("kitPeca.id", kitPecaId);
    }

    public PanacheQuery<JogoXadrez> findByTabuleiro(Long tabuleiroId) {
        return find("tabuleiro.id", tabuleiroId);
    }
}
