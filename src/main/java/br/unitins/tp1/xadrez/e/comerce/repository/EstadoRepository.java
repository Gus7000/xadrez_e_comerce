package br.unitins.tp1.xadrez.e.comerce.repository;

import br.unitins.tp1.xadrez.e.comerce.model.Estado;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EstadoRepository implements PanacheRepository<Estado> {

    public PanacheQuery<Estado> findByNome(String nome) {
        return find("SELECT e FROM Estado e WHERE UPPER(e.nome) LIKE UPPER(?1)",
                "%" + nome + "%");
    }

    public PanacheQuery<Estado> findBySigla(String sigla) {
        return find("SELECT e FROM  Estado e WHERE UPPER(e.nome) = LIKE UPPER(?1)",
                sigla);
    }

}
