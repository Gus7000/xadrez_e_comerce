package br.unitins.tp1.xadrez.e.comerce.repository;

import br.unitins.tp1.xadrez.e.comerce.model.Relogio;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RelogioRepository implements PanacheRepository<Relogio> {
    public PanacheQuery<Relogio> findByMarca(String marca){
        return find("SELECT r FROM Relogio r WHERE UPPER(r.modelo) LIKE UPPER(?1) order by r.id",
                "%" + marca + "%");
    }
}
