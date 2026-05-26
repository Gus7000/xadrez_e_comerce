package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PagamentoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Pagamento;

public interface PagamentoService {

    List<Pagamento> findAll();

    Pagamento findById(Long id);

    Pagamento findByPedidoId(Long pedidoId);

    Pagamento create(PagamentoRequestDTO dto);

    void update(Long id, PagamentoRequestDTO dto);

    void delete(Long id);

    java.util.List<Pagamento> findByUsuarioId(Long usuarioId);

    void estornar(Long id);
}
