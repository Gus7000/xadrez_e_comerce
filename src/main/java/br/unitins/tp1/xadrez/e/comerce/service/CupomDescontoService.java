package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.model.CupomDesconto;

public interface CupomDescontoService {

    List<CupomDesconto> findAll();

    CupomDesconto findById(Long id);

    CupomDesconto findByCodigo(String codigo);

    CupomDesconto create(CupomDesconto cupom);

    void update(Long id, CupomDesconto cupom);

    void delete(Long id);
}
