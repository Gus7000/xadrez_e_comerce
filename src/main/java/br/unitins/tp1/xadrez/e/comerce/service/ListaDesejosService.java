package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.ListaDesejosRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.ListaDesejos;

public interface ListaDesejosService {

    List<ListaDesejos> findAll();

    ListaDesejos findById(Long id);

    List<ListaDesejos> findByUsuarioId(Long usuarioId);

    ListaDesejos create(ListaDesejosRequestDTO dto);

    void update(Long id, ListaDesejosRequestDTO dto);

    void delete(Long id);

    // User-scoped helpers
    ListaDesejos findOrCreateByUsuarioId(Long usuarioId);

    void addItem(Long usuarioId, Long jogoId);

    void removeItem(Long usuarioId, Long jogoId);
}
