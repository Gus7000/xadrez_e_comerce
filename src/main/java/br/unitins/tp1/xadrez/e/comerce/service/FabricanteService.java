package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.Fabricante;

public interface FabricanteService {
    List<Fabricante> findAll();

    Fabricante findById(Long id);

    List<Fabricante> findByNome(String nome);

    Fabricante findByCnpj(String cnpj);

    Fabricante create(Fabricante fabricante);

    void update(Long id, Fabricante fabricante);

    void delete(Long id);
}
