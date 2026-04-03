package br.unitins.tp1.xadrez.e.comerce.repository;

import br.unitins.tp1.xadrez.e.comerce.model.KitPeca;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class KitPecaRepository implements PanacheRepository<KitPeca> {
}
