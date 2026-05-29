package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;
import br.unitins.tp1.xadrez.e.comerce.model.ListaDesejos;

public interface ListaDesejosService {

    // ==========================================
    // Consultas Gerais (Admin)
    // ==========================================
    List<ListaDesejos> findAll();


    List<ListaDesejos> findByUsuarioId(Long usuarioId);

    // ==========================================
    // Operações do Usuário Logado (/me)
    // ==========================================
    ListaDesejos findMyLista();

    void addItem(Long jogoId);

    void removeItem(Long jogoId);
}