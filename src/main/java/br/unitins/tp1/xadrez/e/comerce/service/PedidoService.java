package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.PedidoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Pedido;
import br.unitins.tp1.xadrez.e.comerce.model.PedidoStatus;

public interface PedidoService {

    List<Pedido> findAll(int page, int size);

    Pedido findById(Long id);

    List<Pedido> findByUsuarioId(Long usuarioId, int page, int size);

    Pedido create(PedidoRequestDTO dto);

    void updateStatus(Long id, PedidoStatus status);

    void delete(Long id);
}
