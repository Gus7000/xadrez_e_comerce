package br.unitins.tp1.xadrez.e.comerce.service;

import java.util.List;

import br.unitins.tp1.xadrez.e.comerce.DTO.EnderecoRequestDTO;
import br.unitins.tp1.xadrez.e.comerce.model.Endereco;

public interface EnderecoService {
    List<Endereco> findAll();

    Endereco findById(Long id);

    List<Endereco> findByUsuarioId(Long usuarioId);

    Endereco create(Long usuarioId, EnderecoRequestDTO dto);

    void update(Long id, Long usuarioId, EnderecoRequestDTO dto);

    void delete(Long id);
}
