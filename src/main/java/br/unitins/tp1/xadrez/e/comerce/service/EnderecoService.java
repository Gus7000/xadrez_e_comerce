package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Endereco;

public interface EnderecoService {

    // ==========================================
    // Consultas Gerais (Admin)
    // ==========================================
    List<Endereco> findAll();


    List<Endereco> findByUsuarioId(Long usuarioId);

    // ==========================================
    // Operações do Usuário Logado (/me)
    // ==========================================
    List<Endereco> findMyEnderecos(); // <-- Alterado para o padrão em inglês

    Endereco create(EnderecoRequestDTO dto);

    void update(Long id, EnderecoRequestDTO dto);

    void delete(Long id);
}