package br.unitins.tp1.xadrez.e.comerce.repository;

import br.unitins.tp1.xadrez.e.comerce.model.Material;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

@ApplicationScoped
public class MaterialRepository implements PanacheRepository<Material> {

    public PanacheQuery<Material> findByNome(String nome) {
        return find("UPPER(nome) LIKE UPPER(?1) order by id", "%" + nome + "%");
    }
}
