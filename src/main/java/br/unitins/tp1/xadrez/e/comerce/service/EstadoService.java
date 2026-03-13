package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;


import br.unitins.tp1.xadrez.e.comerce.model.Estado;

public interface EstadoService {
    List<Estado> findAll();

    Estado findById(Long id);

    Estado findBySigla(String sigla);

    List<Estado> findByNome(String nome);

    Estado create(Estado estado);

    void update(Long id, Estado estado);

    void delete(Long id);
}
